package com.xiaohe66.common.web.resolver;

import com.xiaohe66.common.web.base.EnableAnnotationImportBeanDefinitionRegistrar;

/**
 * @author xiaohe
 * @since 2022.01.06 10:53
 */
public class EnableBodyDtoRegistrar extends EnableAnnotationImportBeanDefinitionRegistrar {

    @Override
    protected Class<?> getRegistrarClass() {
        return BodyDtoResolver.class;
    }
}
