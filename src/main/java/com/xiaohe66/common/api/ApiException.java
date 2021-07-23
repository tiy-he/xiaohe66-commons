package com.xiaohe66.common.api;

/**
 * 当 api 请求异常时抛出（系统异常、请求结果不是 200、连接超时、未知异常等）
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
