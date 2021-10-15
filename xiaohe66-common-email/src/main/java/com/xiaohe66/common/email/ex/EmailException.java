package com.xiaohe66.common.email.ex;

/**
 * @author xiaohe
 * @time 2020.05.25 10:09
 */
public class EmailException extends RuntimeException {

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }

    public EmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
