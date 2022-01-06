package com.xiaohe66.common.web.sign;

import com.xiaohe66.common.web.CommonWebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaohe
 * @since 2022.01.06 11:31
 */
// @Configuration
@Slf4j
public class CheckSignConfiguration {

    @Bean
    public CheckSignAop checkSignAop(CheckSignSecretSupplier secretSupplier,
                                     CommonWebProperties commonWebProperties) {

        log.debug("create CheckSignAop");

        return new CheckSignAop(secretSupplier, commonWebProperties);
    }
}
