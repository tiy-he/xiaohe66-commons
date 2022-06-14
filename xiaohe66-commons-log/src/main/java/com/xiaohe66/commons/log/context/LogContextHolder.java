package com.xiaohe66.commons.log.context;

/**
 * @author xiaohe
 * @since 2022.05.23 16:31
 */
public interface LogContextHolder {

    void set(LogContext context);

    LogContext get();

    LogContext remove();

    void putVariable(String key, Object value);

    <T> T getVariable(String key);

}
