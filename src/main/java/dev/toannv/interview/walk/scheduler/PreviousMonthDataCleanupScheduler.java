package dev.toannv.interview.walk.scheduler;

import dev.toannv.interview.walk.service.step.IStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "walk-service.scheduler.previous-month-data-cleanup.enabled", havingValue = "true")
public class PreviousMonthDataCleanupScheduler {

    private final IStepService stepService;

    @Scheduled(cron = "${walk-service.scheduler.previous-month-data-cleanup.cron}", zone = "Asia/Ho_Chi_Minh")
    public void cleanup() {
        stepService.cleanPreviousMonthData();
    }

}
