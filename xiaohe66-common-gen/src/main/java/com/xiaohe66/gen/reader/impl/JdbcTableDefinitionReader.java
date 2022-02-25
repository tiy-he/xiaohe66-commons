package com.xiaohe66.gen.reader.impl;

import com.xiaohe66.gen.reader.TableDefinitionReader;
import com.xiaohe66.gen.reader.bo.TableDefinition;
import com.xiaohe66.gen.reader.bo.TableField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaohe
 * @since 2022.02.21 17:14
 */
@Slf4j
@RequiredArgsConstructor
public class JdbcTableDefinitionReader implements TableDefinitionReader {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public TableDefinition read(String tableName) {

        List<Map<String, Object>> list = jdbcTemplate.queryForList("show full columns from " + tableName + ";");

        List<TableField> fieldList = list.stream().map(map -> {

            String type = StringUtils.substringBefore(map.get("Type").toString(), "(");
            String fieldName = map.get("Field").toString();

            TableField field = new TableField();
            field.setFieldType(type);
            field.setFieldName(fieldName);

            return field;

        }).collect(Collectors.toList());

        TableDefinition definition = new TableDefinition();
        definition.setTableName(tableName);
        definition.setFields(fieldList);

        return definition;
    }
}
