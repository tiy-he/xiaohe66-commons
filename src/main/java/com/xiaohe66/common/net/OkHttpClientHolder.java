package com.xiaohe66.common.net;

import okhttp3.OkHttpClient;

/**
 * @author xiaohe
 * @time 2020.01.05 15:06
 */
public class OkHttpClientHolder {

    private static volatile OkHttpClient client;

    private OkHttpClientHolder() {
    }

    public static OkHttpClient get() {
        if (client == null) {
            synchronized (OkHttpClientHolder.class) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

}
