package com.xiaohe66.common.api.restful;

import com.xiaohe66.common.api.ApiException;

/**
 * @author xiaohe
 * @time 2021.07.23 10:39
 */
public class RestfulApiException extends ApiException {

    public RestfulApiException() {
    }

    public RestfulApiException(String message) {
        super(message);
    }

    public RestfulApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestfulApiException(Throwable cause) {
        super(cause);
    }
}
