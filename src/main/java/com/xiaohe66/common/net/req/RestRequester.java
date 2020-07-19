package com.xiaohe66.common.net.req;

import com.xiaohe66.common.net.AbstractCallback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.Serializable;

/**
 * @author xiaohe
 * @time 2020.07.17 14:46
 */
public abstract class RestRequester<P, C extends AbstractCallback> {

    protected final String baseUrl;
    protected final String queryUrl;
    protected final OkHttpClient httpClient;

    public RestRequester(String baseUrl, String queryUrl, OkHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.queryUrl = queryUrl;
        this.httpClient = httpClient;
    }

    public void post(P param, C callback) {
        Request request = buildPostRequest(param);
        call(request,callback);
    }

    public void delete(Serializable id, C callback) {
        Request request = buildDelRequest(id);
        call(request,callback);
    }

    public void put(P param, C callback) {
        Request request = buildPutRequest(param);
        call(request,callback);
    }

    public void get(Serializable id, C callback) {
        Request request = buildGetRequest(id);
        call(request,callback);
    }

    public void page(P param, int pageNo, int pageSize, C callback) {
        Request request = buildPageRequest(param, pageNo, pageSize);
        call(request,callback);
    }

    public void call(Request request, C callback){
        httpClient.newCall(request).enqueue(callback);
    }

    protected abstract Request buildPostRequest(P param);

    protected Request buildDelRequest(Serializable id) {
        return new Request.Builder()
                .url(baseUrl + queryUrl + "/" + id)
                .delete()
                .build();
    }

    protected abstract Request buildPutRequest(P param);

    protected Request buildGetRequest(Serializable id) {

        return new Request.Builder()
                .url(baseUrl + queryUrl + "/" + id)
                .delete()
                .build();
    }

    protected abstract Request buildPageRequest(P param, int pageNo, int pageSize);

}
