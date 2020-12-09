package com.xiaohe66.common.email.ex;

/**
 * @author xiaohe
 * @time 2020.12.07 15:25
 */
public class EmailAcquireException extends EmailException{

    public EmailAcquireException() {
    }

    public EmailAcquireException(String message) {
        super(message);
    }

    public EmailAcquireException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAcquireException(Throwable cause) {
        super(cause);
    }

    public EmailAcquireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
