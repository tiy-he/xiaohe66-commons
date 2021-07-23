package com.xiaohe66.common.api.okhttp;

import com.xiaohe66.common.api.ApiException;
import okhttp3.Response;

/**
 * @author xiaohe
 * @time 2021.07.20 18:10
 */
public class OkHttpApiException extends ApiException {

    private final transient Response response;

    public OkHttpApiException(Response response) {
        this.response = response;
    }

    public OkHttpApiException(String message, Response response) {
        super(message);
        this.response = response;
    }

    public OkHttpApiException(String message, Throwable cause) {
        super(message, cause);
        response = null;
    }

    public OkHttpApiException(String message, Throwable cause, Response response) {
        super(message, cause);
        this.response = response;
    }

    public OkHttpApiException(Throwable cause, Response response) {
        super(cause);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
