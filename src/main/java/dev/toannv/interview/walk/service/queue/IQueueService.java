package dev.toannv.interview.walk.service.queue;

import dev.toannv.interview.walk.web.api.model.RecordStepRequest;

public interface IQueueService {

    /**
     * Add to record step queue
     *
     * @param request {@link RecordStepRequest} the request
     */
    void addToRecordStepQueue(final RecordStepRequest request);
}
