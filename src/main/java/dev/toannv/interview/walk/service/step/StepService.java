package dev.toannv.interview.walk.service.step;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import dev.toannv.interview.walk.domain.QStep;
import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.exception.ErrorCode;
import dev.toannv.interview.walk.exception.ValidationException;
import dev.toannv.interview.walk.mapper.IStepMapper;
import dev.toannv.interview.walk.repository.IStepRepository;
import dev.toannv.interview.walk.service.cache.ICacheService;
import dev.toannv.interview.walk.service.steparchive.IStepArchiveService;
import dev.toannv.interview.walk.utils.Constants;
import dev.toannv.interview.walk.web.api.model.GetWeeklyStepsResponse;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class StepService implements IStepService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final IStepRepository stepRepository;
    private final ICacheService cacheService;
    private final IStepArchiveService stepArchiveService;
    private final RedissonClient redissonClient;

    @Value("${walk-service.redis.lock.daily-ranking.duration}")
    private int dailyRankingLockDuration;
    @Value("${walk-service.redis.lock.weekly-ranking.duration}")
    private int weeklyRankingLockDuration;
    @Value("${walk-service.redis.lock.monthly-ranking.duration}")
    private int monthlyRankingLockDuration;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @Async(Constants.AsyncTask.RECORD_STEP_TASK_EXECUTOR)
    public void recordStep(final RecordStepRequest request) {
        // For demo purpose, i don't need to check user existence
        var step = getStepForUpdate(request.getUserId(), LocalDate.now());
        if (ObjectUtils.isEmpty(step)) {
            step = IStepMapper.INSTANCE.toStep(request);
        }

        addStep(step, request.getSteps());
        clearCache(request.getUserId());

        final var entity = stepRepository.save(step);
        log.info("Record step success: {}", entity);
        stepArchiveService.recordStepArchive(step);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Long deleteStepByDate(final Date date, final int limit) {
        final var affectedRows  = new JPADeleteClause(entityManager, QStep.step)
                .where(QStep.step.id.in(new JPAQuery<Step>()
                        .clone(entityManager)
                        .select(QStep.step.id)
                        .from(QStep.step)
                        .where(QStep.step.date.before(date))
                        .limit(limit)))
                .execute();

        if (log.isDebugEnabled()) {
            log.debug("Deleted {} steps before {}", affectedRows, date);
        }

        return affectedRows;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = Constants.CacheName.WEEKLY_STEP, keyGenerator="customKeyGenerator", condition = "#userId != null")
    public GetWeeklyStepsResponse getWeeklySteps(final Long userId) {
        if (log.isDebugEnabled()) {
            log.debug("Get weekly steps for user {}", userId);
        }
        final var weekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        final Date start = Date.from(weekStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        final Date end = Date.from(weekStartDate.plusDays(7).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return getStepsByCriteria(userId, start, end);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = Constants.CacheName.MONTHLY_STEP, keyGenerator="customKeyGenerator", condition = "#userId != null")
    public GetWeeklyStepsResponse getMonthlySteps(final Long userId) {
        if (log.isDebugEnabled()) {
            log.debug("Get monthly steps for user {}", userId);
        }
        final var monthStartDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

        final Date start = Date.from(monthStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        final Date end = Date.from(monthStartDate.plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return getStepsByCriteria(userId, start, end);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void refreshDailyRanking() {
        log.info("<START> Refresh daily ranking");
        final RLock lock = redissonClient.getLock(StringUtils.join(Constants.RedisLock.DAILY_RANKING));
        if (lock.isLocked()) {
            log.info("<SKIP> Refresh daily ranking, because it is running by another process");
            return;
        }

        cacheService.clearDailyRankingCache();
        lock.lock(dailyRankingLockDuration, TimeUnit.SECONDS);
        log.info("<LOCKED> Refresh daily ranking");
        try {
            stepRepository.refreshDailyRanking();
            log.info("<UPDATED> Refresh daily ranking");
        } finally {
            lock.unlock();
            log.info("<UNLOCKED> Refresh daily ranking");
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void refreshWeeklyRanking() {
        log.info("<START> Refresh weekly ranking");
        final RLock lock = redissonClient.getLock(StringUtils.join(Constants.RedisLock.WEEKLY_RANKING));
        if (lock.isLocked()) {
            log.info("<SKIP> Refresh weekly ranking, because it is running by another process");
            return;
        }

        lock.lock(weeklyRankingLockDuration, TimeUnit.SECONDS);
        log.info("<LOCKED> Refresh weekly ranking");
        try {
            stepRepository.refreshWeeklyRanking();
            log.info("<UPDATED> Refresh weekly ranking");
        } finally {
            lock.unlock();
            log.info("<UNLOCKED> Refresh weekly ranking");
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void refreshMonthlyRanking() {
        log.info("<START> Refresh monthly ranking");
        final RLock lock = redissonClient.getLock(StringUtils.join(Constants.RedisLock.MONTHLY_RANKING));
        if (lock.isLocked()) {
            log.info("<SKIP> Refresh monthly ranking, because it is running by another process");
            return;
        }

        lock.lock(monthlyRankingLockDuration, TimeUnit.SECONDS);
        log.info("<LOCKED> Refresh monthly ranking");
        try {
            stepRepository.refreshMonthlyRanking();
            log.info("<UPDATED> Refresh monthly ranking");
        } finally {
            lock.unlock();
            log.info("<UNLOCKED> Refresh monthly ranking");
        }
    }

    private void addStep(Step step, int steps) {
        try {
            step.setSteps(Math.addExact(ObjectUtils.defaultIfNull(step.getSteps(), 0), steps));
        } catch(final ArithmeticException e) {
            throw new ValidationException("Steps is too large", ErrorCode.STEPS_TOO_LARGE);
        }
    }

    private void clearCache(final Long userId) {
        cacheService.clearWeeklyStepCache(userId);
        cacheService.clearMonthlyStepCache(userId);
    }

    private Step getStepForUpdate(final Long userId, final LocalDate localDate) {
        final var date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));

        return stepRepository.findOne(new JPAQuery<Step>()
                .from(QStep.step)
                .where(QStep.step.userId.eq(userId).and(QStep.step.date.eq(date)))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE));
    }

    private GetWeeklyStepsResponse getStepsByCriteria(final Long userId, final Date startDate, final Date endDate) {
        final var steps = stepRepository.findAll(new JPAQuery<Step>()
                .from(QStep.step)
                .where(QStep.step.userId.eq(userId)
                        .and(QStep.step.date.goe(startDate))
                        .and(QStep.step.date.lt(endDate))));

        if (CollectionUtils.isEmpty(steps)) {
            return new GetWeeklyStepsResponse()
                    .userId(userId)
                    .totalSteps(0)
                    .data(Collections.emptyList());
        }

        return new GetWeeklyStepsResponse()
                .userId(userId)
                .totalSteps(steps.stream().mapToInt(Step::getSteps).sum())
                .data(steps.stream().map(IStepMapper.INSTANCE::toWeeklySteps).toList());
    }

}
