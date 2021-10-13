package com.xiaohe66.common.net.xh;

import com.xiaohe66.common.net.Page;
import com.xiaohe66.common.net.ex.RequesterException;
import com.xiaohe66.common.net.ex.RequesterResultCodeException;
import com.xiaohe66.common.net.req.RestFormRequester;
import com.xiaohe66.common.reflect.ParamType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.07.17 14:46
 */
public class AbstractXhRequester<E, G> extends RestFormRequester<Integer, Boolean, Boolean, G, Page<G>, E> {

    private static final Class<Result> resultCls = Result.class;
    private static final Class<Page> pageCls = Page.class;
    private static final Type postBeanType = new ParamType(resultCls, Integer.class);
    private static final Type deleteBeanType = new ParamType(resultCls, Boolean.class);
    private static final Type putBeanType = deleteBeanType;
    private final Type pageBeanType;
    private final Type beanType;

    public AbstractXhRequester(String baseUrl, String queryUrl, OkHttpClient httpClient, Type beanType) {
        super(baseUrl, queryUrl, httpClient);
        pageBeanType = new ParamType(resultCls, new ParamType(pageCls, beanType));

        this.beanType = new ParamType(resultCls, beanType);
    }

    @Override
    protected Request buildPageRequest(E p, int pageNo, int pageSize) {
        return new Request.Builder()
                .url(baseUrl + queryUrl)
                .header("pageNo", String.valueOf(pageNo))
                .header("pageSize", String.valueOf(pageSize))
                .get()
                .build();
    }

    @Override
    protected <T> T toBean(Response response, Type beanType) throws RequesterException {
        Result<T> result = super.toBean(response, beanType);

        if (result.getCode() == 401) {
            throw new RequesterResultCodeException(result);
        }

        return result.getData();
    }

    @Override
    public Type postBeanType() {
        return postBeanType;
    }

    @Override
    public Type deleteBeanType() {
        return deleteBeanType;
    }

    @Override
    public Type putBeanType() {
        return putBeanType;
    }

    @Override
    public Type getBeanType() {
        return beanType;
    }

    @Override
    public Type pageBeanType() {
        return pageBeanType;
    }

}
