package com.xiaohe66.common.net.ex;

import com.xiaohe66.common.ex.XhException;

/**
 * @author xiaohe
 * @time 2020.07.22 10:56
 */
public class RequesterException extends XhException {

    public RequesterException() {
    }

    public RequesterException(String message) {
        super(message);
    }

    public RequesterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequesterException(Throwable cause) {
        super(cause);
    }

    public RequesterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
