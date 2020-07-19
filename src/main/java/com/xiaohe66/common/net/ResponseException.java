package com.xiaohe66.common.net;

import com.xiaohe66.common.ex.XhException;

/**
 * @author xiaohe
 * @time 2020.07.17 17:30
 */
public class ResponseException extends XhException {

    public ResponseException() {
    }

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseException(Throwable cause) {
        super(cause);
    }

    public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
