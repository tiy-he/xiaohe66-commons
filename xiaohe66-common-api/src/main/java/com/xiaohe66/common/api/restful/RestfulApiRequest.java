package com.xiaohe66.common.api.restful;

import com.xiaohe66.common.api.BaseApiResponse;
import com.xiaohe66.common.api.okhttp.BaseOkHttpApiRequest;

/**
 * @author xiaohe
 * @time 2021.07.23 10:25
 */
public class RestfulApiRequest<E extends BaseApiResponse>
        extends BaseOkHttpApiRequest<E>
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
    protected Class<E> getResponseClass() {
        return responseCls;
    }
}