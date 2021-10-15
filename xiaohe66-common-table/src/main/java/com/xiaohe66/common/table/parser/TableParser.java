package com.xiaohe66.common.table.parser;

import java.io.File;

/**
 * @author xiaohe
 * @time 2020.04.13 12:56
 */
public interface TableParser {

    /**
     * 解析表格
     *
     * @param file     待解析的文件
     * @param callback 回调
     */
    void parse(File file, TableParseCallback callback);
}
