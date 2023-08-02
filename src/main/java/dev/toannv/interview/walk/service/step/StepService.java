package dev.toannv.interview.walk.service.step;

import com.querydsl.jpa.impl.JPAQuery;
import dev.toannv.interview.walk.domain.QStep;
import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.exception.ErrorCode;
import dev.toannv.interview.walk.exception.ValidationException;
import dev.toannv.interview.walk.mapper.IStepMapper;
import dev.toannv.interview.walk.repository.IStepRepository;
import dev.toannv.interview.walk.service.steparchive.IStepArchiveService;
import dev.toannv.interview.walk.service.user.IUserService;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class StepService implements IStepService {

    private final IStepRepository stepRepository;
    private final IUserService userService;
    private final IStepArchiveService stepArchiveService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Step recordStep(final RecordStepRequest request) {
        final var existedUser = userService.existedById(request.getUserId());
        if (!existedUser) {
            throw new ValidationException(String.format("User with id %s not found", request.getUserId()), ErrorCode.USER_NOT_FOUND);
        }

        var step = getStepForUpdate(request.getUserId(), LocalDate.now());
        if (ObjectUtils.isEmpty(step)) {
            step = IStepMapper.INSTANCE.toStep(request);
        }

        addStep(step, request.getSteps());
        final var entity = stepRepository.save(step);
        stepArchiveService.recordStepArchive(step);
        return entity;
    }

    private void addStep(Step step, int steps) {
        try {
            step.setSteps(Math.addExact(ObjectUtils.defaultIfNull(step.getSteps(), 0), steps));
        } catch(final ArithmeticException e) {
            throw new ValidationException("Steps is too large", ErrorCode.STEPS_TOO_LARGE);
        }
    }

    private Step getStepForUpdate(final Long userId, final LocalDate localDate) {
        final var date = Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.UTC));

        return stepRepository.findOne(new JPAQuery<Step>()
                .from(QStep.step)
                .where(QStep.step.userId.eq(userId).and(QStep.step.date.eq(date)))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE));
    }

}
