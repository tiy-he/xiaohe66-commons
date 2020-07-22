package com.xiaohe66.common.net.req;

import com.xiaohe66.common.net.IRequesterCallback;
import com.xiaohe66.common.net.ex.RequesterException;
import okhttp3.Request;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.07.21 17:37
 */
public interface IRestRequester<C, D, U, G, P, E> {

    C post(E param) throws RequesterException;

    void post(E param, IRequesterCallback<C> callback);

    D delete(Serializable id) throws RequesterException;

    void delete(Serializable id, IRequesterCallback<D> callback);

    U put(E param) throws RequesterException;

    void put(E param, IRequesterCallback<U> callback);

    G get(Serializable id) throws RequesterException;

    void get(Serializable id, IRequesterCallback<G> callback);

    P page(E param, int pageNo, int pageSize) throws RequesterException;

    void page(E param, int pageNo, int pageSize, IRequesterCallback<P> callback);

    <T> T call(Request request, Type beanType) throws RequesterException;

    <T> void call(Request request, Type beanType, IRequesterCallback<T> callback);

    Type postBeanType();

    Type deleteBeanType();

    Type putBeanType();

    Type getBeanType();

    Type pageBeanType();

}
