package com.xiaohe66.common.api.okhttp;

import com.fasterxml.jackson.databind.JavaType;
import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.IApiCallback;
import com.xiaohe66.common.api.IApiModel;
import com.xiaohe66.common.api.IApiRequest;
import com.xiaohe66.common.api.IApiResponse;
import com.xiaohe66.common.util.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class SimpleOkHttpApiClientTest2 {

    @Test
    public void test1() {

        SimpleOkHttpApiClient client = new SimpleOkHttpApiClient(null, "http://localhost:8080") {

            @Override
            public String executeAsString(IApiRequest<?> apiRequest) throws ApiException {
                Request request = buildRequest(apiRequest);

                log.info("request : {}", JsonUtils.toString(apiRequest));
                log.info("url : {}", request.url());

                String bodyString = "";
                RequestBody body = request.body();
                if (body != null) {

                    Buffer buffer = new Buffer();

                    try {
                        body.writeTo(buffer);
                        bodyString = buffer.readUtf8();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                log.info("body : {}", bodyString);

                return "{\"code\":0,\"msg\":\"\",\"data\":{\"value\":1}}";
            }

            @Override
            protected void doExecuteAsync(IApiRequest<?> apiRequest, IApiCallback<?> callback, Supplier<okhttp3.Callback> supplier) {

                log.info("request : {}", JsonUtils.toString(apiRequest));

                String responseBody = "{\"code\":0,\"msg\":\"\",\"data\":{\"value\":1}}";

                IApiResponse o;
                try {
                    o = apiRequest.buildResponseBody(responseBody);
                } catch (ApiException e) {
                    callback.onFail(e);
                    return;
                }

                callback(callback, o);
            }

            protected void callback(IApiCallback callback, IApiResponse response) {
                callback.onSuccess(response);
            }
        };

        MyModel model = new MyModel();
        model.setTest("666");

        MyRequest request = new MyRequest();
        request.setModel(model);

        MyErrorRequest errorRequest = new MyErrorRequest();
        errorRequest.setModel(model);

        try {
            log.info("-------------myResponse get : {}", client.execute(request));

            client.executeAsync(request, response -> log.info("get response async: {}", response));
            client.executeAsync(errorRequest, new IApiCallback<MyResponse<D>>() {
                @Override
                public void onSuccess(MyResponse<D> response) {
                    log.info("get response async : {}", response);
                }

                @Override
                public void onFail(ApiException e) {
                    log.info("error msg : {}", e.getMessage());
                }
            });


        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class MyModel implements IApiModel {

        private String test;

    }

    public static class MyRequest extends FormOkHttpApiRequest<MyResponse<D>> {

        public MyRequest() {
            super(Method.GET);
        }

        @Override
        public String getQueryUrl() {
            return "/test";
        }

        @Override
        public JavaType getResponseType() {
            return JsonUtils.constructType(MyResponse.class, D.class);
        }
    }

    public static class MyErrorRequest extends FormOkHttpApiRequest<MyResponse<D>> {

        public MyErrorRequest() {
            super(Method.GET);
        }

        @Override
        public String getQueryUrl() {
            return "test";
        }

        @Override
        public JavaType getResponseType() {
            return JsonUtils.constructType(MyResponse.class, List.class);
        }

    }

    @Data
    public static class MyResponse<T> implements IApiResponse {

        private String body;

        private Integer code;
        private String msg;
        private T data;

        @Override
        public boolean isSuccess() {
            return code == 0;
        }

    }

    @Data
    public static class D {

        private Integer value;
    }
}