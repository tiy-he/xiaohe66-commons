package com.xiaohe66.common.net.req;

import com.google.gson.JsonSyntaxException;
import com.xiaohe66.common.net.IRequesterCallback;
import com.xiaohe66.common.net.ex.RequesterException;
import com.xiaohe66.common.net.ex.RequesterHttpCodeException;
import com.xiaohe66.common.net.ex.RequesterJsonSyntaxException;
import com.xiaohe66.common.util.JsonUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.07.17 14:46
 */
public abstract class RestRequester<C, D, U, G, P, E> implements IRestRequester<C, D, U, G, P, E> {

    protected final String baseUrl;
    protected final String queryUrl;
    protected final String fullUrl;
    protected final OkHttpClient httpClient;

    public RestRequester(String baseUrl, String queryUrl, OkHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.queryUrl = queryUrl;
        this.fullUrl = baseUrl + queryUrl;
        this.httpClient = httpClient;
    }

    @Override
    public C post(E param) throws RequesterException {
        Request request = buildPostRequest(param);
        return call(request, postBeanType());
    }

    @Override
    public void post(E param, IRequesterCallback<C> callback) {
        Request request = buildPostRequest(param);
        call(request, postBeanType(), callback);
    }

    @Override
    public D delete(Serializable id) throws RequesterException {
        Request request = buildDelRequest(id);
        return call(request, deleteBeanType());
    }

    @Override
    public void delete(Serializable id, IRequesterCallback<D> callback) {
        Request request = buildDelRequest(id);
        call(request, deleteBeanType(), callback);
    }

    @Override
    public U put(E param) throws RequesterException {
        Request request = buildPutRequest(param);
        return call(request, putBeanType());
    }

    @Override
    public void put(E param, IRequesterCallback<U> callback) {
        Request request = buildPutRequest(param);
        call(request, putBeanType(), callback);
    }

    @Override
    public G get(Serializable id) throws RequesterException {
        Request request = buildGetRequest(id);
        return call(request, getBeanType());
    }

    @Override
    public void get(Serializable id, IRequesterCallback<G> callback) {
        Request request = buildGetRequest(id);
        call(request, getBeanType(), callback);
    }

    @Override
    public P page(E param, int pageNo, int pageSize) throws RequesterException {
        Request request = buildPageRequest(param, pageNo, pageSize);
        return call(request, pageBeanType());
    }

    @Override
    public void page(E param, int pageNo, int pageSize, IRequesterCallback<P> callback) {
        Request request = buildPageRequest(param, pageNo, pageSize);
        call(request, pageBeanType(), callback);
    }

    @Override
    public <T> T call(Request request, Type beanType) throws RequesterException {
        try {
            Response response = httpClient.newCall(request).execute();
            return toBean(response, beanType);

        } catch (IOException e) {
            throw new RequesterException("execute request error", e);
        }
    }

    @Override
    public <T> void call(Request request, Type beanType, IRequesterCallback<T> callback) {
        httpClient.newCall(request).enqueue(new RestRequesterCallback<>(beanType, callback));
    }

    protected abstract Request buildPostRequest(E param);

    protected Request buildDelRequest(Serializable id) {
        return new Request.Builder()
                .url(baseUrl + queryUrl + "/" + id)
                .delete()
                .build();
    }

    protected abstract Request buildPutRequest(E param);

    protected Request buildGetRequest(Serializable id) {

        return new Request.Builder()
                .url(baseUrl + queryUrl + "/" + id)
                .get()
                .build();
    }

    protected abstract Request buildPageRequest(E param, int pageNo, int pageSize);

    protected <T> T toBean(Response response, Type beanType) throws RequesterException {
        if (!response.isSuccessful()) {
            throw new RequesterHttpCodeException(response);
        }

        ResponseBody body = response.body();

        if (body == null) {
            throw new RequesterException("response body is null");
        }

        String bodyString;
        try {
            bodyString = body.string();

        } catch (IOException e) {
            throw new RequesterException("cannot get body string", e);
        }

        try {
            return JsonUtils.formatObject(bodyString, beanType);

        } catch (JsonSyntaxException e) {
            throw new RequesterJsonSyntaxException("cannot syntax json", bodyString);
        }
    }

    protected class RestRequesterCallback<T> implements Callback {

        private Type beanType;
        private IRequesterCallback<T> callback;

        public RestRequesterCallback(Type beanType, IRequesterCallback<T> callback) {
            this.beanType = beanType;
            this.callback = callback;
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            callback.onFail(call, new RequesterException("request is failure", e));
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            try {
                T bean = toBean(response, beanType);
                callback.onSuccess(call, bean);

            } catch (RequesterException e) {
                callback.onFail(call, e);

            } catch (Exception e) {
                callback.onFail(call, new RequesterException("unknown exception",e));
            }
        }
    }

}
