package dev.toannv.interview.walk.service.step;

import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.web.api.model.GetWeeklyStepsResponse;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;

public interface IStepService {

    /**
     * Record step of user
     *
     * @param request {@link RecordStepRequest} the request
     * @return {@link Step} the step
     */
    Step recordStep(RecordStepRequest request);

    /**
     * Get weekly steps of user
     *
     * @param userId {@link Long} the user id
     * @return {@link GetWeeklyStepsResponse} the response
     */
    GetWeeklyStepsResponse getWeeklySteps(Long userId);

    /**
     * Get monthly steps of user
     *
     * @param userId {@link Long} the user id
     * @return {@link GetWeeklyStepsResponse} the response
     */
    GetWeeklyStepsResponse getMonthlySteps(Long userId);

    /**
     * Clean previous month data
     */
    void cleanPreviousMonthData();

    /**
     * Refresh daily ranking
     */
    void refreshDailyRanking();

    /**
     * Refresh weekly ranking
     */
    void refreshWeeklyRanking();

    /**
     * Refresh monthly ranking
     */
    void refreshMonthlyRanking();
}
