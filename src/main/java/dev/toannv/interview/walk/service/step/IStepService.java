package dev.toannv.interview.walk.service.step;

import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;

public interface IStepService {

    /**
     * Record step of user
     *
     * @param request {@link RecordStepRequest} the request
     * @return {@link Step} the step
     */
    Step recordStep(RecordStepRequest request);
}
