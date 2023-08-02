package dev.toannv.interview.walk.service.steparchive;

import dev.toannv.interview.walk.domain.Step;

public interface IStepArchiveService {

    /**
     * Record step of user
     *
     * @param step {@link Step} the step
     */
    void recordStepArchive(Step step);
}
