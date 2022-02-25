package com.xiaohe66.gen;

import com.xiaohe66.gen.template.bo.CodeTemplateProperty;
import com.xiaohe66.gen.template.impl.prop.MapperCodeBuildProperty;
import com.xiaohe66.gen.template.impl.prop.RepositoryCodeBuildProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * @author xiaohe
 * @since 2022.02.25 11:29
 */
@Data
@Builder
public class DefaultCodeBuildProperty {

    @NonNull
    private final String entityPackage;

    @NonNull
    private final String mapperPackage;

    @NonNull
    private final String repositoryPackage;

    private final Class<?> entityBaseClass;
    private final Class<?> mapperBaseClass;
    private final Class<?> repositoryBaseClass;

    public CodeTemplateProperty toEntityCodeBuildProperty() {

        CodeTemplateProperty property = new CodeTemplateProperty();
        property.setBaseClassName(entityBaseClass.getSimpleName());
        property.setBaseClassPackage(entityBaseClass.getPackageName());
        property.setClassPackage(entityPackage);

        return property;
    }

    public MapperCodeBuildProperty toMapperCodeBuildProperty() {

        MapperCodeBuildProperty property = new MapperCodeBuildProperty();
        property.setBaseClassName(mapperBaseClass.getSimpleName());
        property.setBaseClassPackage(mapperBaseClass.getPackageName());
        property.setClassPackage(mapperPackage);
        property.setEntityPackage(entityPackage);

        return property;
    }

    public RepositoryCodeBuildProperty toRepositoryCodeBuildProperty() {

        RepositoryCodeBuildProperty property = new RepositoryCodeBuildProperty();
        property.setBaseClassName(repositoryBaseClass.getSimpleName());
        property.setBaseClassPackage(repositoryBaseClass.getPackageName());
        property.setClassPackage(repositoryPackage);
        property.setEntityPackage(entityPackage);
        property.setMapperPackage(mapperPackage);

        return property;
    }

}
