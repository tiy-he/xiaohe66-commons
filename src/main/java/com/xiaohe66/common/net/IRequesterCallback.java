package com.xiaohe66.common.net;

import com.xiaohe66.common.net.ex.RequesterException;
import okhttp3.Call;

/**
 * @author xiaohe
 * @time 2020.07.17 17:28
 */
public interface IRequesterCallback<T> {

    void onSuccess(Call call, T bean);

    default void onFail(Call call, RequesterException e){

    }
}
