package com.xiaohe66.common.api.okhttp;

import okhttp3.OkHttpClient;

/**
 * @author xiaohe
 * @time 2020.01.05 15:06
 */
public class OkHttpClientHolder {

    // todo : change param
    private static OkHttpClient client = new OkHttpClient();

    private OkHttpClientHolder() {
    }

    public static OkHttpClient get() {
        return client;
    }

}
