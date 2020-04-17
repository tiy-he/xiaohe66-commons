package com.xiaohe66.common.table.writer;

import com.xiaohe66.common.table.entity.SimpleWriterContext;
import com.xiaohe66.common.table.entity.TableConfig;
import com.xiaohe66.common.table.entity.TableField;
import com.xiaohe66.common.table.entity.WriterContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaohe
 * @time 2020.04.16 11:59
 */
public class SimpleTableWriter implements TableWriter {

    private static SimpleTableWriter simpleTableWriter = new SimpleTableWriter();

    protected SimpleTableWriter() {
    }

    public static SimpleTableWriter getInstance() {
        return simpleTableWriter;
    }

    @Override
    public void writer(WriterContext context, TableConfig config, List<Map<String, Object>> dataList) {

        if (!(context instanceof SimpleWriterContext)) {
            throw new IllegalStateException("SimpleTableWriter 使用了非 SimpleWriterContext");
        }

        SimpleWriterContext writerContext = (SimpleWriterContext) context;

        Map<String, Object> map = new HashMap<>();

        List<String> titleList = showTitleList(config.getFieldList());

        map.put("title", titleList);
        map.put("items", dataList);

        writerContext.setData(map);
    }

    private List<String> showTitleList(List<TableField> fieldList) {

        return fieldList.stream()
                .filter(TableField::isShow)
                .map(field -> field.getShowTitle() != null ? field.getShowTitle() : field.getTableTitle())
                .collect(Collectors.toList());

    }

}
