package com.xiaohe66.common.api.okhttp;

import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.BaseApiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

/**
 * @author xiaohe
 * @time 2021.07.14 14:49
 */
@Slf4j
public class SimpleOkHttpApiClient extends AbstractOkHttpApiClient {

    public SimpleOkHttpApiClient(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);
    }

    public <T extends BaseApiResponse> void executeAsync(BaseOkHttpApiRequest<T> apiRequest, CallbackObject<T> callback) throws ApiException {
        doExecuteAsync(apiRequest, callback);
    }

    public void executeAsStringAsync(BaseOkHttpApiRequest<?> apiRequest, CallbackString callback) throws ApiException {
        doExecuteAsync(apiRequest, callback);
    }
}
