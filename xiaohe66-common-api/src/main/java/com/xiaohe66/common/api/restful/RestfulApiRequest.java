package com.xiaohe66.common.api.restful;

import com.fasterxml.jackson.databind.JavaType;
import com.xiaohe66.common.api.IApiResponse;
import com.xiaohe66.common.api.okhttp.FormOkHttpApiRequest;

/**
 * @author xiaohe
 * @time 2021.07.23 10:25
 */
public class RestfulApiRequest<E extends IApiResponse>
        extends FormOkHttpApiRequest<E>
        implements IRestfulApiRequest<E> {

    protected final String queryUrl;
    protected final Class<E> responseCls;

    protected RestfulApiRequest(Method method, String queryUrl, Class<E> responseCls) {
        super(method);
        this.queryUrl = queryUrl;
        this.responseCls = responseCls;
    }

    @Override
    public String getQueryUrl() {
        return queryUrl;
    }

    @Override
    public JavaType getResponseType() {
        return constructType(responseCls);
    }
}