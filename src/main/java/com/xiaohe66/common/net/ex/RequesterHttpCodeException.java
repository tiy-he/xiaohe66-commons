package com.xiaohe66.common.net.ex;

import okhttp3.Response;

/**
 * @author xiaohe
 * @time 2020.07.22 10:57
 */
public class RequesterHttpCodeException extends RequesterException {

    private final transient Response response;

    public RequesterHttpCodeException(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
