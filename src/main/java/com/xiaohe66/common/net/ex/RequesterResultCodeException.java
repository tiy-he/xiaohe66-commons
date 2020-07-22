package com.xiaohe66.common.net.ex;

import com.xiaohe66.common.net.xh.Result;

/**
 * @author xiaohe
 * @time 2020.07.17 17:30
 */
public class RequesterResultCodeException extends RequesterException {

    private final transient Result<?> result;

    public RequesterResultCodeException(Result<?> result) {
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public <T> Result<T> getResult() {
        return (Result<T>) result;
    }
}
