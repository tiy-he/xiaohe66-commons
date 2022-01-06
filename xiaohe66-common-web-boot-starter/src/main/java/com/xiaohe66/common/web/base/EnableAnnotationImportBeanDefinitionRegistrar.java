package com.xiaohe66.common.web.base;

import com.xiaohe66.common.web.sign.CheckSignAop;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author xiaohe
 * @since 2022.01.06 10:54
 */
public abstract class EnableAnnotationImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(getRegistrarClass());

        String beanName = generateBaseBeanName(importingClassMetadata);

        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }

    protected abstract Class<?> getRegistrarClass();

    protected String generateBaseBeanName(AnnotationMetadata importingClassMetadata) {

        // note : 抄 mybatis-MapperScannerRegistrar
        return importingClassMetadata.getClassName() + "#" + CheckSignAop.class.getSimpleName();
    }
}
