package dev.toannv.interview.walk.scheduler;

import dev.toannv.interview.walk.service.step.IStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "walk-service.scheduler.ranking.enabled", havingValue = "true")
public class RankingScheduler {

    private final IStepService stepService;

    @Scheduled(fixedDelayString = "${walk-service.scheduler.ranking.daily}", initialDelay = 3000)
    public void refreshDailyRanking() {
        stepService.refreshDailyRanking();
    }

    /**
     * If we have feature to get weekly ranking, we can enable this scheduler
     * <p>
     * scheduler @Scheduled(fixedDelayString = "${walk-service.scheduler.ranking.weekly}", initialDelay = 3000)
     */
    public void refreshWeeklyRanking() {
        stepService.refreshWeeklyRanking();
    }

    /**
     * If we have feature to get monthly ranking, we can enable this scheduler
     * <p>
     * scheduler @Scheduled(fixedDelayString = "${walk-service.scheduler.ranking.monthly}", initialDelay = 3000)
     */
    public void refreshMonthlyRanking() {
        stepService.refreshMonthlyRanking();
    }

}
