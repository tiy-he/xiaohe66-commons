package com.xiaohe66.common.web.sec.config;

import com.xiaohe66.common.web.sec.SecurityService;
import com.xiaohe66.common.web.sec.DefaultSecurityServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaohe
 * @since 2022.01.17 13:52
 */
@ConditionalOnProperty(value = "xiaohe66.web.permission", havingValue = "true", matchIfMissing = false)
@Configuration
@ComponentScan(basePackageClasses = SecConfig.class)
public class SecConfig {

    @Bean
    @ConditionalOnMissingBean
    public SecurityService securityService() {
        return new DefaultSecurityServiceImpl();
    }

}
