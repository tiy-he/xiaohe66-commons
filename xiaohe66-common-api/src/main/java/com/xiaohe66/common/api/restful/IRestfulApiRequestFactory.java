package com.xiaohe66.common.api.restful;

import com.xiaohe66.common.api.BaseApiResponse;
import com.xiaohe66.common.api.IApiModel;

import java.io.Serializable;

/**
 * @author xiaohe
 * @time 2021.07.23 14:51
 */
public interface IRestfulApiRequestFactory<E extends BaseApiResponse> {

    /**
     * 构造 restful get 请求
     *
     * @param id id
     * @return IRestfulApiRequest
     */
    IRestfulApiRequest<E> get(Serializable id);

    /**
     * 构造 restful post 请求
     *
     * @param model 实体
     * @return IRestfulApiRequest
     */
    IRestfulApiRequest<E> post(IApiModel model);

    /**
     * 构造 restful put 请求
     *
     * @param model 实体
     * @return IRestfulApiRequest
     */
    IRestfulApiRequest<E> put(IApiModel model);

    /**
     * 构造 restful delete 请求
     *
     * @param id id
     * @return IRestfulApiRequest
     */
    IRestfulApiRequest<E> delete(Serializable id);

    /**
     * 返回实体类 class 对象
     *
     * @return 实体类 class 对象
     */
    Class<E> getResponseCls();

}
