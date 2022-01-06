package com.xiaohe66.common.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiaohe
 * @since 2022.01.05 15:48
 */
@Data
@ConfigurationProperties(prefix = "xiaohe66.web")
public class CommonWebProperties {

    private boolean bodyResolver;
    private boolean checkSign;
    private long checkSignTimeoutMs = 1000 * 60 * 5;

}
