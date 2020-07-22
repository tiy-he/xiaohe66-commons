package com.xiaohe66.common.net;

import com.xiaohe66.common.net.xh.AbstractXhRequester;

import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.07.22 10:45
 */
public class XhRequesterImpl<P, R> extends AbstractXhRequester<P, R> {

    public XhRequesterImpl(String queryUrl, Type beanCls) {
        super("http://localhost:8080", queryUrl, OkHttpClientHolder.get(), beanCls);
    }


}
