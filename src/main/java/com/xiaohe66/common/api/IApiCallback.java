package com.xiaohe66.common.api;

/**
 * @author xiaohe
 * @time 2021.07.20 12:05
 */
public interface IApiCallback<T> {

    /**
     * 成功时回调
     *
     * @param response 结果类
     */
    void onSuccess(T response);

    /**
     * 失败或异常时回调
     *
     * @param e 异常
     */
    default void onFail(ApiException e) {

    }
}
