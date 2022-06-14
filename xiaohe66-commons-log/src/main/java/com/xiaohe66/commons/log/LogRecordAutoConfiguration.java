package com.xiaohe66.commons.log;

import com.xiaohe66.commons.log.tool.LogRecordService;
import com.xiaohe66.commons.log.context.LogContextHolder;
import com.xiaohe66.commons.log.context.impl.ThreadLocalLogContextHolder;
import com.xiaohe66.commons.log.tool.LogRecordAsyncExecutor;
import com.xiaohe66.commons.log.tool.impl.LogRecordAsyncExecutorSimpleImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaohe
 * @since 2022.05.23 17:49
 */
@Configuration
@Slf4j
public class LogRecordAutoConfiguration {

    public LogRecordAutoConfiguration() {
        log.debug("instance LogRecordConfiguration");
    }

    @Bean
    @ConditionalOnMissingBean
    public LogContextHolder logContext() {
        log.debug("instance LogContextHolder");
        return new ThreadLocalLogContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogRecordAsyncExecutor logRecordAsyncExecutor() {
        log.debug("instance LogRecordAsyncExecutor");
        return new LogRecordAsyncExecutorSimpleImpl();
    }

    @Bean
    @ConditionalOnBean(LogRecordService.class)
    public LogRecordAspect logRecordAspect(LogContextHolder logContextHolder,
                                           LogRecordService logSaveService,
                                           LogRecordAsyncExecutor logRecordAsyncExecutor) {

        log.debug("instance LogRecordAspect");
        return new LogRecordAspect(logContextHolder, logSaveService, logRecordAsyncExecutor);
    }

}
