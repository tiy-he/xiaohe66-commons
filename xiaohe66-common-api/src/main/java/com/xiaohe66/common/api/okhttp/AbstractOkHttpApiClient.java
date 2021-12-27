package com.xiaohe66.common.api.okhttp;

import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.ApiSupplier;
import com.xiaohe66.common.api.BuildRequestBodyException;
import com.xiaohe66.common.api.IApiCallback;
import com.xiaohe66.common.api.IApiClient;
import com.xiaohe66.common.api.IApiRequest;
import com.xiaohe66.common.api.IApiResponse;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    public <T extends IApiResponse> void executeAsync(IApiRequest<T> apiRequest, IApiCallback<T> callback) {

        doExecuteAsync(apiRequest, callback, () -> new CallbackObject<T>(apiRequest) {
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
    public void executeAsStringAsync(IApiRequest<?> apiRequest, IApiCallback<String> callback) {

        doExecuteAsync(apiRequest, callback, () -> new CallbackString() {
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

    protected void doExecuteAsync(IApiRequest<?> apiRequest, IApiCallback<?> callback, Supplier<okhttp3.Callback> supplier) {
        Request request;
        try {
            request = buildRequest(apiRequest);

        } catch (BuildRequestBodyException e) {
            callback.onFail(e);
            return;
        }

        if (callback instanceof okhttp3.Callback) {

            client.newCall(request).enqueue((okhttp3.Callback) callback);
        } else {

            client.newCall(request).enqueue(supplier.get());
        }
    }

    protected Request buildRequest(IApiRequest<?> apiRequest) throws BuildRequestBodyException {
        if (!(apiRequest instanceof BaseOkHttpApiRequest)) {
            throw new ClassCastException("cannot cast IApiRequest to BaseOkHttpApiRequest");
        }
        return buildRequest(apiRequest, ((BaseOkHttpApiRequest<?>) apiRequest)::buildRequestBody);
    }

    protected Request buildRequest(IApiRequest<?> apiRequest, ApiSupplier<RequestBody> requestBodySupplier) throws BuildRequestBodyException {

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

                requestBody = getRequestBody(requestBodySupplier);

                builder = new Request.Builder()
                        .url(fullUrl)
                        .post(requestBody);

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();

            case PUT:

                requestBody = getRequestBody(requestBodySupplier);

                builder = new Request.Builder()
                        .url(fullUrl)
                        .put(requestBody);

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();

            case DELETE:

                requestBody = getRequestBody(requestBodySupplier);

                builder = new Request.Builder()
                        .url(fullUrl)
                        .delete(requestBody);

                apiRequest.getHeader().forEach(builder::header);

                return builder.build();

            default:
                throw new UnsupportedOperationException("unknown method:" + apiRequest.getMethod());
        }
    }

    protected final RequestBody getRequestBody(ApiSupplier<RequestBody> requestBodySupplier) throws BuildRequestBodyException {
        try {
            return requestBodySupplier.get();

        } catch (BuildRequestBodyException e) {
            throw e;

        } catch (ApiException e) {
            throw new BuildRequestBodyException(e);
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
        default void onFailure(@NonNull Call call, @NonNull IOException e) {
            onFail(new OkHttpApiException("request is failure", e));
        }
    }

    public abstract static class CallbackObject<T extends IApiResponse> implements Callback<T> {

        @Getter
        private final IApiRequest<T> request;

        public CallbackObject(IApiRequest<T> request) {
            this.request = request;
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) {

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
        default void onResponse(@NonNull Call call, @NonNull Response response) {

            try {
                String responseString = getResponseString(response);
                onSuccess(responseString);

            } catch (ApiException e) {
                onFail(e);
            }
        }
    }
}
