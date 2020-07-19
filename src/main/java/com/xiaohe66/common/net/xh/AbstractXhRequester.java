package com.xiaohe66.common.net.xh;

import com.xiaohe66.common.net.req.RestFormRequester;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public abstract class AbstractXhRequester<P, C extends AbstractXhCallback> extends RestFormRequester<P, C> {

    public AbstractXhRequester(String baseUrl, String queryUrl, OkHttpClient httpClient) {
        super(baseUrl, queryUrl, httpClient);
    }

    @Override
    protected Request buildPageRequest(P p, int pageNo, int pageSize) {
        return new Request.Builder()
                .url(baseUrl + queryUrl)
                .header("pageNo", String.valueOf(pageNo))
                .header("pageSize", String.valueOf(pageSize))
                .get()
                .build();
    }


}
