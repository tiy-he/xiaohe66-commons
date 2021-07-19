package com.xiaohe66.common.net.req;

import com.google.gson.JsonSyntaxException;
import com.xiaohe66.common.net.IRequesterCallback;
import com.xiaohe66.common.net.ex.RequesterException;
import com.xiaohe66.common.net.ex.RequesterHttpCodeException;
import com.xiaohe66.common.net.ex.RequesterJsonSyntaxException;
import com.xiaohe66.common.util.GsonUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2021.01.06 16:37
 */
public abstract class AbstractHttpRequester<P, R> implements IHttpRequester<P, R, IRequesterCallback<R>> {

    protected final String baseUrl;
    protected final String queryUrl;
    protected final String fullUrl;
    protected final OkHttpClient httpClient;
    protected final Type beanType;

    public AbstractHttpRequester(String baseUrl, String queryUrl, OkHttpClient httpClient, Type beanType) {
        this.baseUrl = baseUrl;
        this.queryUrl = queryUrl;
        this.beanType = beanType;
        this.fullUrl = baseUrl + queryUrl;
        this.httpClient = httpClient;
    }


    @Override
    public R call(P param) throws RequesterException {

        Request request = buildRequest(param);

        Response response;
        try {
            response = httpClient.newCall(request).execute();

        } catch (IOException e) {
            throw new RequesterException("execute request error", e);
        }

        return toBean(response, beanType);
    }

    @Override
    public void call(P param, IRequesterCallback<R> callback) {

        Request request = buildRequest(param);

        httpClient.newCall(request).enqueue(new HttpRequestCallback<>(beanType, callback));
    }

    protected abstract Request buildRequest(P param);

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
            return GsonUtils.formatObject(bodyString, beanType);

        } catch (JsonSyntaxException e) {
            throw new RequesterJsonSyntaxException("cannot syntax json", bodyString);
        }
    }

    protected class HttpRequestCallback<T> implements Callback {

        private Type beanType;
        private IRequesterCallback<T> callback;

        protected HttpRequestCallback(Type beanType, IRequesterCallback<T> callback) {
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
                callback.onFail(call, new RequesterException("unknown exception", e));
            }
        }
    }
}
