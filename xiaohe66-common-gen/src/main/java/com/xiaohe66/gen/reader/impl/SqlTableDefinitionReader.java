package com.xiaohe66.gen.reader.impl;

import com.xiaohe66.gen.reader.TableDefinitionReader;
import com.xiaohe66.gen.reader.bo.TableDefinition;
import com.xiaohe66.gen.reader.bo.TableField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohe
 * @since 2022.02.24 16:27
 */
@Slf4j
@RequiredArgsConstructor
public class SqlTableDefinitionReader implements TableDefinitionReader {

    @Override
    public TableDefinition read(String sql) {

        String[] split = StringUtils.split(sql, "\n", 2);
        String split2 = StringUtils.substringBefore(split[1], "PRIMARY");

        String tableName = readTableName(split[0]);

        TableDefinition definition = new TableDefinition();
        definition.setTableName(tableName);
        definition.setFields(readField(split2));

        return definition;
    }

    private String readTableName(String firstLine) {
        return StringUtils.substringBetween(firstLine, "`", "`");
    }

    private List<TableField> readField(String sql) {

        String[] lines = sql.split("\n");

        List<TableField> list = new ArrayList<>();

        for (String line : lines) {

            try {
                if (StringUtils.isNotBlank(line)) {
                    TableField field = readLine(line);
                    list.add(field);
                }

            } catch (Exception e) {
                log.error("{} : {},", e.getMessage(), line);
            }
        }

        return list;
    }

    private TableField readLine(String line) {

        String[] split = line.split("` ");

        String name = StringUtils.substringAfter(split[0], "`");

        String type = StringUtils.split(split[1], " ", 2)[0];

        type = StringUtils.substringBefore(type, "(");

        log.info("{} : {}", type, name);

        TableField field = new TableField();
        field.setFieldType(type);
        field.setFieldName(name);

        return field;
    }

}
