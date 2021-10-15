package com.xiaohe66.common.table.parser;


import com.xiaohe66.common.table.entity.ParserContext;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.13 12:40
 */
public interface TableParseCallback {

    /**
     * 读取工作簿开始时回调,整个解析过程仅回调一次
     *
     * @param parserContext ParserContext
     */
    default void onOpen(ParserContext parserContext) {

    }

    /**
     * 读取工作表开始时回调,若有一个文件有多个工作表,则会回调多次
     *
     * @param parserContext 上下文
     * @return 可控制是否继续往下读取，返回true继续往下读取
     */
    default boolean onStart(ParserContext parserContext) {
        return true;
    }

    /**
     * 发生错误时回调
     *
     * @param parserContext 上下文
     * @param e             异常
     */
    default void onError(ParserContext parserContext, Exception e) {
        // todo : 目前暂未启用本方法
    }

    /**
     * 下一行
     *
     * @param parserContext 上下文
     * @param data          行数据
     */
    void onNextRow(ParserContext parserContext, List<Object> data);

    /**
     * 读取工作表结束时回调,若有一个文件有多个工作表,则会回调多次
     *
     * @param parserContext 上下文
     */
    void onEnd(ParserContext parserContext);

    /**
     * 读取工作簿结束时回调,整个解析过程仅回调一次
     *
     * @param parserContext ParserContext
     */
    default void onClose(ParserContext parserContext) {

    }

}
