package com.xiaohe66.common.api;

/**
 * 无法构建请求时抛出
 *
 * @author xiaohe
 * @since 2021.12.27 15:45
 */
public class BuildRequestBodyException extends ApiException {

    public BuildRequestBodyException() {
    }

    public BuildRequestBodyException(String message) {
        super(message);
    }

    public BuildRequestBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildRequestBodyException(Throwable cause) {
        super(cause);
    }
}
