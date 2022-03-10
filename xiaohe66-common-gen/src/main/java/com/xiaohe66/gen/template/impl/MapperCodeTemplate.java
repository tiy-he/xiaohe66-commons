package com.xiaohe66.gen.template.impl;

import com.xiaohe66.gen.template.AbstractCodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.template.impl.prop.MapperCodeBuildProperty;
import org.apache.velocity.VelocityContext;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaohe
 * @since 2022.02.22 17:10
 */
public class MapperCodeTemplate extends AbstractCodeTemplate {

    public MapperCodeTemplate(MapperCodeBuildProperty property) {
        super(property, "vm/mapper.vm");
    }

    public MapperCodeTemplate(MapperCodeBuildProperty property, String templateFileName) {
        super(property, templateFileName);
    }

    @Override
    public void gen(JavaDefinition definition, Writer writer) {

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("package", property.getClassPackage());

        if (property.getBaseClassName() != null) {
            velocityContext.put("baseClassPackage", property.getBaseClassPackage());
            velocityContext.put("baseClass", property.getBaseClassName());
        }

        velocityContext.put("entityName", definition.getClassName());

        MapperCodeBuildProperty property = (MapperCodeBuildProperty) this.property;
        velocityContext.put("entityPackage", property.getEntityPackage());

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        velocityContext.put("time", time);

        template.merge(velocityContext, writer);
    }

    @Override
    public String genFileName(JavaDefinition definition) {
        return definition.getClassName() + "Mapper.java";
    }
}
