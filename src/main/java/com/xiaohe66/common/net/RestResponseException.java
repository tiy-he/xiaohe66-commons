package com.xiaohe66.common.net;

import com.xiaohe66.common.ex.XhException;

/**
 * @author xiaohe
 * @time 2020.07.17 17:30
 */
public class RestResponseException extends XhException {

    public RestResponseException() {
    }

    public RestResponseException(String message) {
        super(message);
    }

    public RestResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestResponseException(Throwable cause) {
        super(cause);
    }

    public RestResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
