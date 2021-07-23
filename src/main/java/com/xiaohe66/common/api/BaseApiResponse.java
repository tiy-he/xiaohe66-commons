package com.xiaohe66.common.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohe
 * @time 2021.07.14 14:30
 */
public abstract class BaseApiResponse {

    /**
     * 响应的字符串, response body 中的完整数据
     */
    @Getter
    @Setter
    private String response;

    /**
     * 判断响应是否成功（除了响应为200外，还需要业务参数为成功。若不能解析 json, 也可视为失败）
     *
     * @return 返回true表示成功，返回false表示失败
     */
    public abstract boolean isSuccess();

}
