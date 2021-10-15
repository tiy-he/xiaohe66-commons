package com.xiaohe66.common.table.writer;

import com.xiaohe66.common.table.entity.ExcelWriterContext;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.WriterContext;

import java.util.List;
import java.util.Map;

/**
 * @author xiaohe
 * @time 2020.04.16 12:15
 */
public class ExcelTableWriter implements TableWriter {

    @Override
    public void writer(WriterContext context, TableConfig config, List<Map<String, Object>> dataList) {
        if (!(context instanceof ExcelWriterContext)) {
            throw new IllegalStateException("ExcelTableWriter 使用了非 ExcelWriterContext");
        }
        // todo : Impl
        throw new UnsupportedOperationException();
    }
}
