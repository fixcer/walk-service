package dev.toannv.interview.walk.service.steparchive;

import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.repository.IStepArchiveRepository;
import dev.toannv.interview.walk.utils.Constants;
import dev.toannv.interview.walk.utils.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class StepArchiveService implements IStepArchiveService {

    private final IStepArchiveRepository stepArchiveRepository;

    @Override
    @Async(Constants.AsyncTask.STEP_ARCHIVE_TASK_EXECUTOR)
    @Transactional(rollbackFor = Throwable.class)
    public void recordStepArchive(Step step) {
        if (log.isDebugEnabled()) {
            log.debug("Record step archive: {}", step);
        }

        final var stepArchiveId = Snowflake.nextId();
        stepArchiveRepository.upsertStepArchive(
                stepArchiveId,
                step.getId(),
                step.getUserId(),
                step.getSteps(),
                step.getDate(),
                step.getSteps());

        if (log.isDebugEnabled()) {
            log.debug("Record step archive success: {}", stepArchiveId);
        }
    }

}
