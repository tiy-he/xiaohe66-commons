package com.xiaohe66.common.net.req;

import com.xiaohe66.common.net.AbstractRestCallback;
import com.xiaohe66.common.util.JsonUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author xiaohe
 * @time 2020.07.17 15:01
 */
public abstract class RestJsonRequester<P, C extends AbstractRestCallback> extends RestHttpRequester<P, C> {

    protected static final MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

    public RestJsonRequester(String baseUrl, String queryUrl, OkHttpClient httpClient) {
        super(baseUrl, queryUrl, httpClient);
    }

    @Override
    protected Request buildSaveRequest(P param) {

        RequestBody requestBody = RequestBody.create(JsonUtils.toString(param), jsonMediaType);

        return new Request.Builder()
                .url(baseUrl + queryUrl)
                .post(requestBody)
                .build();
    }

    @Override
    protected Request buildUpdateRequest(P param) {
        RequestBody requestBody = RequestBody.create(JsonUtils.toString(param), jsonMediaType);

        return new Request.Builder()
                .url(baseUrl + queryUrl)
                .post(requestBody)
                .build();
    }

}
