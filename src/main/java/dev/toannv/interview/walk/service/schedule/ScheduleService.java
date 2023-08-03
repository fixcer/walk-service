package dev.toannv.interview.walk.service.schedule;

import dev.toannv.interview.walk.repository.IScheduleRepository;
import dev.toannv.interview.walk.service.step.IStepService;
import dev.toannv.interview.walk.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {

    private final IScheduleRepository scheduleRepository;
    private final RedissonClient redissonClient;
    private final IStepService stepService;

    @Value("${walk-service.redis.lock.previous-month-data-cleanup.duration:10}")
    private int previousMonthDataCleanupLockDuration;
    @Value("${walk-service.scheduler.previous-month-data-cleanup.limit:10000}")
    private int limitDeleteSteps;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void cleanPreviousMonthData() {
        log.info("<START> Clean previous month data");
        var schedule = scheduleRepository.findByCode(Constants.CLEAN_PREVIOUS_MONTH_DATA);
        if (Objects.isNull(schedule)) {
            log.warn("<SKIP> Clean previous month data, because schedule not found");
            return;
        }

        final var now = new Date();
        if (now.before(schedule.getNextRunAt())) {
            log.info("<SKIP> Clean previous month data, because it is not time yet, next run at: {}", schedule.getNextRunAt());
            return;
        }

        final RLock lock = redissonClient.getLock(StringUtils.join(Constants.RedisLock.CLEAN_PREVIOUS_MONTH_DATA));
        if (lock.isLocked()) {
            log.info("<SKIP> Clean previous month data, because daily ranking is locked");
            return;
        }

        lock.lock(previousMonthDataCleanupLockDuration, TimeUnit.SECONDS);
        log.info("<LOCKED> Clean previous month data");
        try {
            log.info("<RUN> Clean previous month data");
            final var weekStartDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            final Date date = Date.from(weekStartDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            var affectedRows = stepService.deleteStepByDate(date, limitDeleteSteps);
            if (affectedRows < limitDeleteSteps) {
                final var monthStartDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                final Date nextRunAt = Date.from(monthStartDate.plusMonths(1)
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .plusHours(-7)
                        .toInstant());
                schedule.setNextRunAt(nextRunAt);
                scheduleRepository.save(schedule);
            }

            log.info("<FINISH> Clean previous month data");
        } finally {
            try {
                lock.unlock();
                log.info("<UNLOCK> Clean previous month data");
            } catch (final Exception e) {
                log.warn("<UNLOCK FAILED> Clean previous month data");
            }
        }
    }

}
