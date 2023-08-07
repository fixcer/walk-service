package dev.toannv.interview.walk.service.cache;


import dev.toannv.interview.walk.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CacheService implements ICacheService {

    @Override
    @CacheEvict(value = Constants.CacheName.WEEKLY_STEP, keyGenerator = "customKeyGenerator")
    public void clearWeeklyStepCache(final Long userId) {
        log.info("Clear weekly step cache for user {}", userId);
    }

    @Override
    @CacheEvict(value = Constants.CacheName.MONTHLY_STEP, keyGenerator = "customKeyGenerator")
    public void clearMonthlyStepCache(final Long userId) {
        log.info("Clear monthly step cache for user {}", userId);
    }

    @Override
    @CacheEvict(value = Constants.CacheName.DAILY_RANKING, allEntries = true)
    public void clearDailyRankingCache() {
        log.info("Clear daily ranking cache for all entries");
    }

}
