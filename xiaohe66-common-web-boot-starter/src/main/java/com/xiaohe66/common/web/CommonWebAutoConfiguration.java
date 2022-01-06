package com.xiaohe66.common.web;

import com.xiaohe66.common.web.resolver.BodyDtoResolver;
import com.xiaohe66.common.web.sign.CheckSignAop;
import com.xiaohe66.common.web.sign.CheckSignSecretSupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

/**
 * @author xiaohe
 * @since 2022.01.05 15:44
 */
@Slf4j
@Configuration
public class CommonWebAutoConfiguration {

    @Bean
    public CommonWebProperties commonWebProperties() {
        return new CommonWebProperties();
    }

    @ConditionalOnProperty(value = "xiaohe66.web.bodyResolver", havingValue = "true", matchIfMissing = true)
    @Bean
    public BodyDtoResolver bodyDtoResolver(Validator validator) {
        log.debug("create BodyDtoResolver bean");
        return new BodyDtoResolver(validator);
    }

    @ConditionalOnProperty(value = "xiaohe66.web.checkSign", havingValue = "true", matchIfMissing = true)
    @ConditionalOnBean(CheckSignSecretSupplier.class)
    @Bean
    public CheckSignAop checkSignAop(CheckSignSecretSupplier secretSupplier,
                                     CommonWebProperties commonWebProperties) {

        log.debug("create CheckSignAop");

        return new CheckSignAop(secretSupplier, commonWebProperties);
    }

    @ConditionalOnProperty(value = "xiaohe66.web.globalControllerAdvice", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    @Bean
    public GlobalControllerAdvice globalControllerAdvice() {

        log.debug("create GlobalControllerAdvice");

        return new GlobalControllerAdvice();
    }

}
