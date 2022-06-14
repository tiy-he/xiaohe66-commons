package com.xiaohe66.commons.log.context.impl;

import com.xiaohe66.commons.log.context.LogContext;
import com.xiaohe66.commons.log.context.LogContextHolder;

import java.util.Map;

/**
 * @author xiaohe
 * @since 2022.05.23 16:13
 */
public class ThreadLocalLogContextHolder implements LogContextHolder {

    public static final ThreadLocal<LogContext> LOG_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void set(LogContext context) {
        LOG_CONTEXT_THREAD_LOCAL.set(context);
    }

    @Override
    public LogContext get() {
        return LOG_CONTEXT_THREAD_LOCAL.get();
    }

    @Override
    public LogContext remove() {
        LogContext logContext = LOG_CONTEXT_THREAD_LOCAL.get();
        LOG_CONTEXT_THREAD_LOCAL.remove();
        return logContext;
    }

    @Override
    public void putVariable(String key, Object value) {

        LogContext logContext = LOG_CONTEXT_THREAD_LOCAL.get();
        if (logContext == null) {
            return;
        }

        Map<String, Object> variableMap = logContext.getVariableMap();

        variableMap.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getVariable(String key) {

        LogContext logContext = LOG_CONTEXT_THREAD_LOCAL.get();
        if (logContext == null) {
            return null;
        }

        Map<String, Object> variableMap = logContext.getVariableMap();

        return variableMap != null ? (T) variableMap.get(key) : null;
    }

}
