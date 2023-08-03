package dev.toannv.interview.walk.config;

import dev.toannv.interview.walk.utils.Constants;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static dev.toannv.interview.walk.utils.Constants.Character.DASH;


@Configuration
@EnableCaching
public class CacheConfiguration {

    @Value("${walk-service.redis.prefix:dev}")
    private String redisPrefix;

    @Value("${walk-service.redis.time-to-live:604_800_000}")
    private long timeToLive;

    @Value("${walk-service.redis.max-idle-time:86_400_000}")
    private long maxIdleTime;

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        config.put(Constants.CacheName.WEEKLY_STEP, new CacheConfig(timeToLive, maxIdleTime));
        config.put(Constants.CacheName.MONTHLY_STEP, new CacheConfig(timeToLive, maxIdleTime));
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> redisPrefix + StringUtils.arrayToDelimitedString(params, DASH);
    }

}
