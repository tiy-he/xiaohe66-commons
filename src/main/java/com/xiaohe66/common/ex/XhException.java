package com.xiaohe66.common.ex;

/**
 * @author xiaohe
 * @time 2019.10.17 10:02
 */
public class XhException extends Exception{

    public XhException() {
    }

    public XhException(String message) {
        super(message);
    }

    public XhException(String message, Throwable cause) {
        super(message, cause);
    }

    public XhException(Throwable cause) {
        super(cause);
    }

    public XhException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
