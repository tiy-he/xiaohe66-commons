package com.xiaohe66.commons.log.tool;

import com.xiaohe66.commons.log.context.LogContext;

/**
 * 操作日志的特殊处理类超类，可以用该实现类做一些特殊操作，例如存放参数。
 *
 * @author xiaohe
 * @since 2022.05.20 17:06
 */
public interface LogRecordExtend<T> {

    /**
     * 切面方法执行前
     *
     * @return T 自定义参数，返回值后，会传递给其他方法。最佳实践是使用该方式记录切面方法执行前的记录，如记录老数据。
     */
    default T executeBefore(LogContext context) {
        return null;
    }

    /**
     * 切面方法执行后，日志模板解析前，可以在这个方法里修改模板参数
     *
     * @param data {@link LogRecordExtend#executeBefore} 方法的返回值
     * @return 是否继续执行后续的日志操作。true:继续, false: 中断，即不执行后续操作。
     */
    default boolean parseBefore(LogContext context, T data) {
        return true;
    }

    /**
     * 日志模板解析后，可以在这个方法里修改日志记录的最终值
     *
     * @param data {@link LogRecordExtend#executeBefore} 方法的返回值
     * @return 是否继续执行后续的日志操作。true:继续, false: 中断，即不执行后续操作。
     */
    default boolean parseAfter(LogContext context, T data) {
        return true;
    }

    /**
     * 日志保存后，最后执行
     *
     * @param data {@link LogRecordExtend#executeBefore} 方法的返回值
     */
    default void saveAfter(LogContext context, T data) {

    }

}
