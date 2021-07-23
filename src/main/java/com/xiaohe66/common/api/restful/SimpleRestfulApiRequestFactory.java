package com.xiaohe66.common.api.restful;

import com.xiaohe66.common.api.BaseApiResponse;
import com.xiaohe66.common.api.IApiModel;
import com.xiaohe66.common.api.IApiRequest;

import java.io.Serializable;

/**
 * @author xiaohe
 * @time 2021.07.23 14:55
 */
public class SimpleRestfulApiRequestFactory<E extends BaseApiResponse> implements IRestfulApiRequestFactory<E> {

    private final String queryUrl;
    private final Class<E> responseCls;

    public SimpleRestfulApiRequestFactory(String queryUrl, Class<E> responseCls) {
        this.queryUrl = queryUrl;
        this.responseCls = responseCls;
    }

    @Override
    public IRestfulApiRequest<E> get(Serializable id) {

        String fullQueryUrl = this.queryUrl + "/" + id;

        return new RestfulApiRequest<>(IApiRequest.Method.GET, fullQueryUrl, responseCls);
    }

    @Override
    public IRestfulApiRequest<E> post(IApiModel model) {

        RestfulApiRequest<E> apiRequest = new RestfulApiRequest<>(IApiRequest.Method.POST, queryUrl, responseCls);
        apiRequest.setModel(model);

        return apiRequest;
    }

    @Override
    public IRestfulApiRequest<E> put(IApiModel model) {

        RestfulApiRequest<E> apiRequest = new RestfulApiRequest<>(IApiRequest.Method.PUT, queryUrl, responseCls);
        apiRequest.setModel(model);

        return apiRequest;
    }

    @Override
    public IRestfulApiRequest<E> delete(Serializable id) {

        String fullQueryUrl = this.queryUrl + "/" + id;

        return new RestfulApiRequest<>(IApiRequest.Method.DELETE, fullQueryUrl, responseCls);
    }

    @Override
    public Class<E> getResponseCls() {
        return responseCls;
    }
}
