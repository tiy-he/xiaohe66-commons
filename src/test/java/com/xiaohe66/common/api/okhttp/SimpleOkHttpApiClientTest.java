package com.xiaohe66.common.api.okhttp;

import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.BaseApiResponse;
import com.xiaohe66.common.api.IApiModel;
import com.xiaohe66.common.api.IApiRequest;
import com.xiaohe66.common.api.restful.SimpleRestfulApiRequestFactory;
import com.xiaohe66.common.util.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class SimpleOkHttpApiClientTest {

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
            protected void doExecuteAsync(IApiRequest<?> apiRequest, okhttp3.Callback callback) throws ApiException {

                log.info("request : {}", JsonUtils.toString(apiRequest));

                Callback callback1 = (Callback) callback;

                String responseBody = "{\"code\":0,\"msg\":\"\",\"data\":{\"value\":1}}";

                callback1.onSuccess(apiRequest.buildResponseBody(responseBody));
            }
        };

        SimpleRestfulApiRequestFactory<MyResponse> factory = new SimpleRestfulApiRequestFactory<>("/test", MyResponse.class);

        MyModel model = new MyModel();
        model.setTest("666");

        try {
            log.info("-------------myResponse get : {}", client.execute(factory.get(1)));
            log.info("-------------myResponse post : {}", client.execute(factory.post(model)));
            log.info("-------------myResponse put : {}", client.execute(factory.put(model)));
            log.info("-------------myResponse delete : {}", client.execute(factory.delete(1)));

            log.info("-------------myResponse post : {}", client.executeAsSuccess(factory.get(1)));
            log.info("-------------myResponse post : {}", client.executeAsSuccess(factory.post(model)));
            log.info("-------------myResponse put : {}", client.executeAsSuccess(factory.put(model)));
            log.info("-------------myResponse delete : {}", client.executeAsSuccess(factory.delete(1)));

            client.executeAsync(factory.get(1), response -> log.info("get response : {}", response));
            client.executeAsync(factory.post(model), response -> log.info("post response : {}", response));
            client.executeAsync(factory.put(model), response -> log.info("put response : {}", response));
            client.executeAsync(factory.delete(1), response -> log.info("delete response : {}", response));


        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Data
    public class MyModel implements IApiModel {

        private String test;

    }

    @Data
    public static class MyResponse extends BaseApiResponse {

        private Integer code;
        private String msg;
        private D data;

        @Override
        public boolean isSuccess() {
            return code == 0;
        }

        @Data
        public static class D {

            private Integer value;
        }

    }
}