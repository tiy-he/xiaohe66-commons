package com.xiaohe66.common.net.req;

import com.xiaohe66.common.util.GsonUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author xiaohe
 * @time 2020.07.17 15:01
 */
public abstract class RestJsonRequester<C, D, U, G, P, E> extends RestRequester<C, D, U, G, P, E> {

    protected static final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

    public RestJsonRequester(String baseUrl, String queryUrl, OkHttpClient httpClient) {
        super(baseUrl, queryUrl, httpClient);
    }

    @Override
    protected Request buildPostRequest(E param) {

        return new Request.Builder()
                .url(baseUrl + queryUrl)
                .post(createJsonBody(param))
                .build();
    }

    @Override
    protected Request buildPutRequest(E param) {

        return new Request.Builder()
                .url(baseUrl + queryUrl)
                .post(createJsonBody(param))
                .build();
    }

    protected RequestBody createJsonBody(String json) {
        return RequestBody.create(json, jsonMediaType);
    }

    protected RequestBody createJsonBody(E param) {
        return RequestBody.create(GsonUtils.toString(param), jsonMediaType);
    }
}
