package com.github.datagenerator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Value("${datagen.core.pool.size:10}")
    private int corePoolSize;
    @Value("${datagen.max.pool.size:20}")
    private int maxPoolSize;
    @Value("${datagen.queue.capacity:100}")
    private int queueCapacity;


    @Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("data-gen-thread-");
        executor.initialize();
        return executor;
    }
}
