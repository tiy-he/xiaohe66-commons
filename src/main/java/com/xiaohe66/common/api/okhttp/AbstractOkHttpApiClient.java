package com.xiaohe66.common.api.okhttp;

import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.BaseApiResponse;
import com.xiaohe66.common.api.IApiCallback;
import com.xiaohe66.common.api.IApiClient;
import com.xiaohe66.common.api.IApiRequest;
import lombok.Getter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * @author xiaohe
 * @time 2021.07.20 18:42
 */
public abstract class AbstractOkHttpApiClient implements IApiClient {

    protected final String baseUrl;
    protected final OkHttpClient client;

    public AbstractOkHttpApiClient(OkHttpClient client, String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
    }

    /**
     * 执行异步请求，将结果以实体类返回
     *
     * @param apiRequest 请求
     * @param callback   回调
     */
    @Override
    public <T extends BaseApiResponse> void executeAsync(IApiRequest<T> apiRequest, IApiCallback<T> callback) throws ApiException {

        if (callback instanceof okhttp3.Callback) {

            doExecuteAsync(apiRequest, (okhttp3.Callback) callback);

        } else {

            doExecuteAsync(apiRequest, new CallbackObject<T>(apiRequest) {
                @Override
                public void onSuccess(T response) {
                    callback.onSuccess(response);
                }

                @Override
                public void onFail(ApiException e) {
                    callback.onFail(e);
                }
            });
        }
    }

    @Override
    public String executeAsString(IApiRequest<?> apiRequest) throws ApiException {

        Request request = buildRequest(apiRequest);

        Response response;
        try {
            response = client.newCall(request).execute();

        } catch (IOException e) {
            throw new ApiException(e);
        }

        return getResponseString(response);
    }

    /**
     * 执行异步请求，将结果以 String 返回
     *
     * @param apiRequest 请求
     * @param callback   回调
     */
    @Override
    public void executeAsStringAsync(IApiRequest<?> apiRequest, IApiCallback<String> callback) throws ApiException {
        if (callback instanceof okhttp3.Callback) {

            doExecuteAsync(apiRequest, (okhttp3.Callback) callback);

        } else {

            doExecuteAsync(apiRequest, new CallbackString() {
                @Override
                public void onSuccess(String responseBody) {
                    callback.onSuccess(responseBody);
                }

                @Override
                public void onFail(ApiException e) {
                    callback.onFail(e);
                }
            });
        }
    }

    protected void doExecuteAsync(IApiRequest<?> apiRequest, okhttp3.Callback callback) throws ApiException {

        Request request = buildRequest(apiRequest);

        client.newCall(request).enqueue(callback);
    }

    protected Request buildRequest(IApiRequest<?> apiRequest) {
        if (!(apiRequest instanceof BaseOkHttpApiRequest)) {
            throw new ClassCastException("cannot cast IApiRequest to BaseOkHttpApiRequest");
        }
        return buildRequest(apiRequest, ((BaseOkHttpApiRequest<?>) apiRequest)::buildRequestBody);
    }

    protected Request buildRequest(IApiRequest<?> apiRequest, Supplier<RequestBody> requestBodySupplier) {

        String fullUrl = baseUrl + apiRequest.getQueryUrl();

        RequestBody requestBody;
        Request.Builder builder;

        switch (apiRequest.getMethod()) {

            case GET:
                builder = new Request.Builder()
                        .url(fullUrl)
                        .get();

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();
            case POST:

                requestBody = requestBodySupplier.get();

                builder = new Request.Builder()
                        .url(fullUrl)
                        .post(requestBody);

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();

            case PUT:

                requestBody = requestBodySupplier.get();

                builder = new Request.Builder()
                        .url(fullUrl)
                        .put(requestBody);

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();

            case DELETE:

                requestBody = requestBodySupplier.get();

                builder = new Request.Builder()
                        .url(fullUrl)
                        .delete(requestBody);

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();

            default:
                throw new UnsupportedOperationException("unknown method:" + apiRequest.getMethod());
        }
    }

    protected static String getResponseString(Response response) throws OkHttpApiException {

        if (!response.isSuccessful()) {
            throw new OkHttpApiException(response);
        }

        ResponseBody body = response.body();
        if (body == null) {
            throw new OkHttpApiException("response body is null", response);
        }

        try {
            return body.string();

        } catch (IOException e) {
            throw new OkHttpApiException("cannot get body string", e, response);
        }
    }

    public interface Callback<T> extends okhttp3.Callback, IApiCallback<T> {

        /**
         * 失败
         *
         * @param call Call
         * @param e    IOException
         */
        @Override
        default void onFailure(@NotNull Call call, @NotNull IOException e) {
            onFail(new OkHttpApiException("request is failure", e));
        }
    }

    public abstract static class CallbackObject<T extends BaseApiResponse> implements Callback<T> {

        @Getter
        private final IApiRequest<T> request;

        public CallbackObject(IApiRequest<T> request) {
            this.request = request;
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) {

            try {
                String responseString = getResponseString(response);
                T object = request.buildResponseBody(responseString);
                onSuccess(object);

            } catch (ApiException e) {
                onFail(e);
            }
        }
    }

    public interface CallbackString extends Callback<String> {

        /**
         * 解析
         *
         * @param call     Call
         * @param response Response
         */
        @Override
        default void onResponse(@NotNull Call call, @NotNull Response response) {

            try {
                String responseString = getResponseString(response);
                onSuccess(responseString);

            } catch (ApiException e) {
                onFail(e);
            }
        }
    }
}
