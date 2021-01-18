package com.xiaohe66.common.net.req;

import com.xiaohe66.common.net.ex.RequesterException;

/**
 * @author xiaohe
 * @time 2020.07.22 09:58
 */
public interface IHttpRequester<P, R, C> {


    R call(P param) throws RequesterException;

    void call(P param, C callback);


}
