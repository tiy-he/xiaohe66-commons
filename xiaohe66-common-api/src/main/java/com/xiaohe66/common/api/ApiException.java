package com.xiaohe66.common.api;

/**
 * 当api异常时抛出，包括构建请求，获取请求结果，构建结果类等
 *
 * @author xiaohe
 * @time 2021.07.14 17:15
 */
public class ApiException extends Exception {

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
