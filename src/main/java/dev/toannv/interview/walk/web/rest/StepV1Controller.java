package dev.toannv.interview.walk.web.rest;

import dev.toannv.interview.walk.mapper.IStepMapper;
import dev.toannv.interview.walk.service.step.IStepService;
import dev.toannv.interview.walk.web.api.StepV1ApiDelegate;
import dev.toannv.interview.walk.web.api.model.GetWeeklyStepsResponse;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;
import dev.toannv.interview.walk.web.api.model.RecordStepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StepV1Controller implements StepV1ApiDelegate {

    private final IStepService stepService;

    @Override
    public ResponseEntity<GetWeeklyStepsResponse> getMonthlySteps(String userId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<GetWeeklyStepsResponse> getWeeklySteps(String userId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<RecordStepResponse> recordStep(final RecordStepRequest request) {
        final var entity = stepService.recordStep(request);
        return ResponseEntity.ok(IStepMapper.INSTANCE.toResponse(entity));
    }

}
