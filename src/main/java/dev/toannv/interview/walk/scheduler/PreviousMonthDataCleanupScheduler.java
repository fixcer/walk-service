package dev.toannv.interview.walk.scheduler;

import dev.toannv.interview.walk.service.schedule.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "walk-service.scheduler.previous-month-data-cleanup.enabled", havingValue = "true")
public class PreviousMonthDataCleanupScheduler {

    private final IScheduleService scheduleService;

    @Scheduled(fixedDelayString = "${walk-service.scheduler.previous-month-data-cleanup.delay}", initialDelay = 2000)
    public void cleanup() {
        scheduleService.cleanPreviousMonthData();
    }

}
