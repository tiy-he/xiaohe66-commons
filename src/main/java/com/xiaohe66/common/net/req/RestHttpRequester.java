package com.xiaohe66.common.net.req;

import com.xiaohe66.common.net.AbstractRestCallback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.Serializable;

/**
 * @author xiaohe
 * @time 2020.07.17 14:46
 */
public abstract class RestHttpRequester<P, C extends AbstractRestCallback> {

    protected final String baseUrl;
    protected final String queryUrl;
    protected final OkHttpClient httpClient;

    public RestHttpRequester(String baseUrl, String queryUrl, OkHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.queryUrl = queryUrl;
        this.httpClient = httpClient;
    }

    public void save(P param, C callback) {
        Request request = buildSaveRequest(param);
        httpClient.newCall(request).enqueue(callback);
    }

    public void delete(Serializable id, C callback) {
        Request request = buildDelRequest(id);
        httpClient.newCall(request).enqueue(callback);
    }

    public void update(P param, C callback) {
        Request request = buildUpdateRequest(param);
        httpClient.newCall(request).enqueue(callback);
    }

    public void query(Serializable id, C callback) {
        Request request = buildQueryRequest(id);
        httpClient.newCall(request).enqueue(callback);
    }

    public void page(P param, int pageNo, int pageSize, C callback) {
        Request request = buildPageRequest(param, pageNo, pageSize);
        httpClient.newCall(request).enqueue(callback);
    }

    protected abstract Request buildSaveRequest(P param);

    protected Request buildDelRequest(Serializable id) {
        return new Request.Builder()
                .url(baseUrl + queryUrl + "/" + id)
                .delete()
                .build();
    }

    protected abstract Request buildUpdateRequest(P param);

    protected Request buildQueryRequest(Serializable id) {

        return new Request.Builder()
                .url(baseUrl + queryUrl + "/" + id)
                .delete()
                .build();
    }

    protected abstract Request buildPageRequest(P param, int pageNo, int pageSize);

}
