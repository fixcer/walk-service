package dev.toannv.interview.walk.web.rest;

import dev.toannv.interview.walk.service.queue.IQueueService;
import dev.toannv.interview.walk.service.step.IStepService;
import dev.toannv.interview.walk.web.api.StepV1ApiDelegate;
import dev.toannv.interview.walk.web.api.model.GetWeeklyStepsResponse;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StepV1Controller implements StepV1ApiDelegate {

    private final IStepService stepService;
    private final IQueueService queueService;

    @Override
    public ResponseEntity<GetWeeklyStepsResponse> getMonthlySteps(final Long userId) {
        return ResponseEntity.ok(stepService.getMonthlySteps(userId));
    }

    @Override
    public ResponseEntity<GetWeeklyStepsResponse> getWeeklySteps(final Long userId) {
        return ResponseEntity.ok(stepService.getWeeklySteps(userId));
    }

    @Override
    public ResponseEntity<Void> recordStep(final RecordStepRequest request) {
        queueService.addToRecordStepQueue(request);
        return ResponseEntity.ok().build();
    }

}
