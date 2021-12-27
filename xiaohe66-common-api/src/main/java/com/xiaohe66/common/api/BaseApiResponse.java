package com.xiaohe66.common.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohe
 * @time 2021.07.14 14:30
 */
public abstract class BaseApiResponse implements IApiResponse {

    /**
     * 响应的字符串, response body 中的完整数据
     */
    @Getter
    @Setter
    private String body;

}
