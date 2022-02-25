package com.xiaohe66.gen.template.impl;

import com.xiaohe66.gen.template.AbstractCodeTemplate;
import com.xiaohe66.gen.template.bo.JavaDefinition;
import com.xiaohe66.gen.template.impl.prop.RepositoryCodeBuildProperty;
import org.apache.velocity.VelocityContext;

import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaohe
 * @since 2022.02.22 17:27
 */
public class RepositoryCodeTemplate extends AbstractCodeTemplate {

    public RepositoryCodeTemplate(RepositoryCodeBuildProperty property) {
        super(property, "vm/repository.vm");
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

        RepositoryCodeBuildProperty property = (RepositoryCodeBuildProperty) this.property;
        velocityContext.put("mapperPackage", property.getMapperPackage());
        velocityContext.put("entityPackage", property.getEntityPackage());

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        velocityContext.put("time", time);

        template.merge(velocityContext, writer);
    }

    @Override
    public String genFileName(JavaDefinition definition) {
        return definition.getClassName() + "Repository.java";
    }
}
