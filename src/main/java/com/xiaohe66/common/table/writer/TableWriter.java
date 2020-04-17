package com.xiaohe66.common.table.writer;

import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.WriterContext;

import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.16 11:30
 */
public interface TableWriter {

    /**
     * 写入数据
     *
     * @param context  写入器上下文
     * @param config   表格配置
     * @param dataList 数据
     */
    void writer(WriterContext context, TableConfig config, List<Map<String, Object>> dataList);

}
