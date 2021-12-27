package com.xiaohe66.common.api;

/**
 * @author xiaohe
 * @since 2021.12.27 14:26
 */
public interface IApiResponse {

    void setBody(String body);

    /**
     * 响应的字符串, response body 中的完整数据
     *
     * @return response body 中的完整数据
     */
    String getBody();

    /**
     * 判断响应是否成功（除了响应为200外，还需要业务参数为成功。若不能解析 json, 也可视为失败）
     *
     * @return 返回true表示成功，返回false表示失败
     */
    boolean isSuccess();

}
