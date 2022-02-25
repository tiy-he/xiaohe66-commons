package com.xiaohe66.gen.template.impl;

import com.xiaohe66.gen.template.AbstractCodeTemplate;
import com.xiaohe66.gen.template.bo.CodeTemplateProperty;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.template.bo.JavaField;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaohe
 * @since 2022.02.22 14:28
 */
@Slf4j
public class EntityCodeTemplate extends AbstractCodeTemplate {

    /**
     * 数据库关键字
     */
    @NonNull
    @Setter
    private List<String> tableKeyword = Arrays.asList("name", "status", "date");

    public EntityCodeTemplate(CodeTemplateProperty property) {
        super(property, "vm/entity.vm");
    }

    @Override
    public void gen(JavaDefinition definition, Writer writer) {

        VelocityContext velocityContext = buildContext(definition);

        template.merge(velocityContext, writer);
    }

    @Override
    public String genFileName(JavaDefinition definition) {
        return definition.getClassName() + ".java";
    }

    private VelocityContext buildContext(JavaDefinition definition) {

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("package", property.getClassPackage());

        if (property.getBaseClassName() != null) {
            velocityContext.put("baseClassPackage", property.getBaseClassPackage());
            velocityContext.put("baseClass", property.getBaseClassName());
        }

        velocityContext.put("className", definition.getClassName());
        velocityContext.put("tableName", definition.getTableDefinition().getTableName());

        List<Map<String, String>> fields = definition.getFields().stream()
                .map(field -> convert(velocityContext, field))
                .collect(Collectors.toList());

        velocityContext.put("fields", fields);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        velocityContext.put("time", time);

        return velocityContext;
    }

    private Map<String, String> convert(VelocityContext velocityContext, JavaField field) {

        if (field.getType() == JavaField.JavaType.LOCAL_DATE) {
            velocityContext.put("hasLocalDate", true);

        } else if (field.getType() == JavaField.JavaType.LOCAL_DATE_TIME) {
            velocityContext.put("hasLocalDateTime", true);
        }

        Map<String, String> map = new HashMap<>();
        map.put("type", field.getType().getValue());
        map.put("name", field.getName());

        if (tableKeyword.contains(field.getName())) {
            velocityContext.put("hasTableField", true);
        }

        return map;
    }
}
