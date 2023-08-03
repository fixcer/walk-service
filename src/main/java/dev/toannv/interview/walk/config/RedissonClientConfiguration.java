package dev.toannv.interview.walk.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonClientConfiguration {

    @Value("${walk-service.redis.url:redis://localhost:6379}")
    private String redisUrl;

    @Value("${walk-service.redis.password:}")
    private String password;

    @Value("${walk-service.redis.client-name:walk-service}")
    private String clientName;

    @Value("${walk-service.redis.ssl:false}")
    private boolean redisSsl;

    @Value("${walk-service.redis.connection-pool-size:64}")
    private int connectionPoolSize;

    @Value("${walk-service.redis.connection-minimum-idle-size:10}")
    private int connectionMinimumIdleSize;

    @Value("${walk-service.redis.subscription-connection-pool-size:50}")
    private int subscriptionConnectionPoolSize;

    @Value("${walk-service.redis.database:0}")
    private int database;

    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        if (StringUtils.isNotBlank(password)) {
            serverConfig.setAddress(redisUrl).setPassword(password);
        } else {
            serverConfig.setAddress(redisUrl);
        }
        serverConfig.setSslEnableEndpointIdentification(redisSsl)
            .setClientName(clientName)
            .setDatabase(database)
            .setConnectionPoolSize(connectionPoolSize)
            .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
            .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }
}
