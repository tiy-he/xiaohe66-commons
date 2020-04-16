package com.xiaohe66.common.table.parser;

import com.xiaohe66.common.table.entity.ParserContext;

import java.util.List;

/**
 * @author xiaohe
 * @time 2020.04.13 12:40
 */
public interface TableParseCallback {

    /**
     * 开始时回调
     *
     * @param parserContext 上下文
     */
    default void onStart(ParserContext parserContext) {

    }

    /**
     * 下一行
     *
     * @param parserContext 上下文
     * @param data          行数据
     */
    void onNextRow(ParserContext parserContext, List<Object> data);

    /**
     * 结束时回调
     *
     * @param parserContext 上下文
     */
    void onEnd(ParserContext parserContext);

}
