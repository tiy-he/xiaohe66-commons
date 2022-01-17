package com.xiaohe66.common.web.sign.priv;

import com.xiaohe66.common.web.base.EnableAnnotationImportBeanDefinitionRegistrar;
import com.xiaohe66.common.web.sign.config.CheckSignAop;

/**
 * @author xiaohe
 * @since 2022.01.05 17:35
 */
public class EnableCheckSignRegistrar extends EnableAnnotationImportBeanDefinitionRegistrar {

    @Override
    protected Class<?> getRegistrarClass() {
        return CheckSignAop.class;
    }
}
