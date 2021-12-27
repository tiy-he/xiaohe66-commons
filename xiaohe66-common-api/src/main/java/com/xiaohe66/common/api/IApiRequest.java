package com.xiaohe66.common.api;

import com.fasterxml.jackson.databind.JavaType;

import java.util.Map;

/**
 * @author xiaohe
 * @time 2021.07.23 11:00
 */
public interface IApiRequest<E extends IApiResponse> {

    enum Method {
        /**
         * 请求类型
         */
        POST, PUT, GET, DELETE
    }

    /**
     * queryUrl,
     * <p>
     * 为了兼容某些参数填在 url 上的情况, 因此不以参数的形式保存，而通过方法返回。
     *
     * @return queryUrl
     */
    String getQueryUrl();

    /**
     * 建立实体类的抽象方法
     *
     * @return 结果实体类
     * @throws BuildRequestBodyException 无法构建请求时抛出
     */
    Object buildRequestBody() throws BuildRequestBodyException;

    /**
     * 构建实体类的方法
     *
     * @param response bodyString
     * @return 实体类
     * @throws ApiException 无法转换实体时抛出
     */
    E buildResponseBody(String response) throws ApiException;

    /**
     * 结果类的 JavaType 对象
     * <p>
     * note : 不在框架内封装getResponseType()为 getResponseClass()，这个由框架的使用方实现
     *
     * @return JavaType
     */
    JavaType getResponseType();

    /**
     * 获取请求类型
     *
     * @return 请求类型
     */
    Method getMethod();

    /**
     * 获取请求的实体类
     *
     * @return 请求的实体类
     */
    IApiModel getModel();


    /**
     * 获取请求头
     *
     * @return 请求头
     */
    Map<String, String> getHeader();
}
