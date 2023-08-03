package dev.toannv.interview.walk.service.queue;


import dev.toannv.interview.walk.service.step.IStepService;
import dev.toannv.interview.walk.utils.Constants;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService implements IQueueService {

    private final RedissonClient redissonClient;
    private final IStepService stepService;

    @Value("${walk-service.redis.queue.record-step-poll}")
    private int recordStepPoll;

    @Override
    public void addToRecordStepQueue(final RecordStepRequest request) {
        RQueue<RecordStepRequest> queue = redissonClient.getQueue(Constants.QUEUE.RECORD_STEP_QUEUE);
        queue.add(request);
    }

    @EventListener(ApplicationStartedEvent.class)
    @SuppressWarnings({"java:S2189", "InfiniteLoopStatement"})
    public void pollFromRecordStepQueue() {
        RQueue<RecordStepRequest> queue = redissonClient.getQueue(Constants.QUEUE.RECORD_STEP_QUEUE);
        while (true) {
            var requests = queue.poll(recordStepPoll);
            if (CollectionUtils.isEmpty(requests)) {
                continue;
            }

            requests.forEach(stepService::recordStep);
        }
    }
}
