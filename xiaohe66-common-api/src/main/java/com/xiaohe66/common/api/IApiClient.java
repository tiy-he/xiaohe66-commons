package com.xiaohe66.common.api;

/**
 * @author xiaohe
 * @time 2021.07.14 14:29
 */
public interface IApiClient {

    /**
     * 执行同步请求，将结果以 String 结果返回
     *
     * @param request 请求
     * @return 结果字符串
     * @throws ApiException 当请求发生异常时抛出
     */
    String executeAsString(IApiRequest<?> request) throws ApiException;

    /**
     * 执行异步请求，将结果以 String 返回
     *
     * @param request  请求
     * @param callback 回调
     * @throws ApiException 当异常时抛出
     */
    void executeAsStringAsync(IApiRequest<?> request, IApiCallback<String> callback);

    /**
     * 执行同步请求，将结果转换成实体类返回
     *
     * @param request 请求
     * @param <T>     结果实体
     * @return 结果实体
     * @throws ApiException 当请求发生异常时抛出
     */
    default <T extends IApiResponse> T execute(IApiRequest<T> request) throws ApiException {
        String body = executeAsString(request);

        try {
            return request.buildResponseBody(body);

        } catch (ApiException e) {
            throw e;

        } catch (Exception e) {
            throw new ApiException("buildResponseBody error, responseString : " + body, e);
        }
    }

    /**
     * 执行异步请求，将结果以实体类返回
     *
     * @param request  请求
     * @param callback 回调
     * @param <T>      结果实体
     * @throws ApiException 当异常时抛出
     */
    <T extends IApiResponse> void executeAsync(IApiRequest<T> request, IApiCallback<T> callback);


    /**
     * 执行同步请求, 执行成功返回 true,否则返回 false
     *
     * @param request IApiRequest
     * @return 是否成功，请求成功且业务也成功时返回 true
     * @throws ApiException 当请求发生异常时抛出
     */
    default boolean executeAsSuccess(IApiRequest<?> request) throws ApiException {
        return execute(request).isSuccess();
    }

    /**
     * 执行异步请求, 执行成功返回 true,否则返回 false
     *
     * @param request  IApiRequest
     * @param callback 回调
     */
    default <T extends IApiResponse> void executeAsSuccessAsync(IApiRequest<T> request, IBooleanApiCallback callback) {
        executeAsync(request, new IApiCallback<T>() {
            @Override
            public void onSuccess(T response) {
                callback.onSuccess(response.isSuccess());
            }

            @Override
            public void onFail(ApiException e) {
                callback.onFail(e);
            }
        });
    }

}
