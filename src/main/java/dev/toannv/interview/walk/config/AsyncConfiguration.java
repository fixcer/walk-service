package dev.toannv.interview.walk.config;

import dev.toannv.interview.walk.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfiguration {

    @Value("${walk-service.executor.record-step.core-pool-size}")
    private int corePoolSize;

    @Value("${walk-service.executor.record-step.max-pool-size}")
    private int maxPoolSize;

    @Value("${walk-service.executor.record-step.queue-capacity}")
    private int queueCapacity;

    @Bean(name = Constants.AsyncTask.RECORD_STEP_TASK_EXECUTOR)
    public Executor rtspTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("record-step-task-");
        executor.initialize();

        return executor;
    }

}
