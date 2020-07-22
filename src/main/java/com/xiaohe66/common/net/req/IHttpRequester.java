package com.xiaohe66.common.net.req;

import okhttp3.Callback;

/**
 * @author xiaohe
 * @time 2020.07.22 09:58
 */
public interface IHttpRequester<P, R, C extends Callback> {


    R call(P param);

    void call(P param, C callback);


}
