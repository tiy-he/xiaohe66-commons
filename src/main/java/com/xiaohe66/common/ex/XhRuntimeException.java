package com.xiaohe66.common.ex;

/**
 * @author xiaohe
 * @time 2019.10.17 10:02
 */
public class XhRuntimeException extends RuntimeException{

    public XhRuntimeException() {
    }

    public XhRuntimeException(String message) {
        super(message);
    }

    public XhRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public XhRuntimeException(Throwable cause) {
        super(cause);
    }

    public XhRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
