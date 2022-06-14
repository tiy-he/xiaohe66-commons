package com.xiaohe66.commons.log.tool.impl;

import com.xiaohe66.commons.log.tool.LogRecordAsyncExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author xiaohe
 * @since 2022.05.27 11:55
 */
public class LogRecordAsyncExecutorSimpleImpl implements LogRecordAsyncExecutor {

    public static final int MAX_QUEUE_SIZE = 1000;

    public final Executor realExecutor;

    public LogRecordAsyncExecutorSimpleImpl() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        //配置队列大小
        executor.setQueueCapacity(MAX_QUEUE_SIZE);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("LogRecord-");
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();

        this.realExecutor = executor;
    }

    public LogRecordAsyncExecutorSimpleImpl(Executor executor) {
        this.realExecutor = executor;
    }

    @Override
    public void execute(Runnable command) {
        realExecutor.execute(command);
    }
}
