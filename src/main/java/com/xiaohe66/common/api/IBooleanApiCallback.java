package com.xiaohe66.common.api;

/**
 * @author xiaohe
 * @time 2021.07.23 10:12
 */
public interface IBooleanApiCallback {

    /**
     * 成功时回调
     *
     * @param response 结果
     */
    void onSuccess(boolean response);

    /**
     * 失败或异常时回调
     *
     * @param e 异常
     */
    default void onFail(ApiException e) {

    }
}
