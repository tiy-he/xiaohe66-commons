package com.xiaohe66.gen.convert.impl;

import com.xiaohe66.gen.convert.DefinitionConverter;
import com.xiaohe66.gen.convert.FieldNameConverter;
import com.xiaohe66.gen.reader.bo.TableDefinition;
import com.xiaohe66.gen.reader.bo.TableField;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.template.bo.JavaField;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaohe
 * @since 2022.02.24 17:48
 */
public class SimpleDefinitionConverter implements DefinitionConverter {

    /**
     * 忽略的字段(数据库字段名)
     */
    @NonNull
    @Setter
    private List<String> ignoreFieldName = Collections.emptyList();

    /**
     * 字段名转换器
     */
    @NonNull
    @Setter
    private FieldNameConverter fieldNameConverter = new UnderlineToCamelCaseConverter();

    @Override
    public JavaDefinition convert(TableDefinition tableDefinition) {

        List<JavaField> fields = tableDefinition.getFields().stream()
                .filter(filed -> !ignoreFieldName.contains(filed.getFieldName()))
                .map(this::convert)
                .collect(Collectors.toList());

        JavaDefinition definition = new JavaDefinition();
        definition.setClassName(tableDefinition.getTableName());
        definition.setFields(fields);
        definition.setTableDefinition(tableDefinition);

        return definition;
    }

    protected JavaField convert(TableField tableField) {

        String name = fieldNameConverter.convert(tableField.getFieldName());

        JavaField field = new JavaField();
        field.setName(name);
        field.setType(JavaField.JavaType.parse(tableField.getFieldType()));
        field.setTableField(tableField);

        return field;
    }

}
