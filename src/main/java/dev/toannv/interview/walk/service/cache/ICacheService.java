package dev.toannv.interview.walk.service.cache;


public interface ICacheService {

    /**
     * Clear weekly step cache for given user
     *
     * @param userId {@link Long} the user id
     */
    void clearWeeklyStepCache(Long userId);

    /**
     * Clear monthly step cache for given user
     *
     * @param userId {@link Long} the user id
     */
    void clearMonthlyStepCache(Long userId);

}
