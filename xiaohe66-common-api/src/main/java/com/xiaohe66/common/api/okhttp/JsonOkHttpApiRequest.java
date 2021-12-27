package com.xiaohe66.common.api.okhttp;

import com.xiaohe66.common.api.IApiModel;
import com.xiaohe66.common.api.IApiResponse;
import com.xiaohe66.common.util.JsonUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author xiaohe
 * @since 2021.12.27 15:32
 */
public abstract class JsonOkHttpApiRequest<R extends IApiResponse> extends BaseOkHttpApiRequest<R> {

    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    public JsonOkHttpApiRequest(Method method) {
        super(method);
    }

    @Override
    public RequestBody buildRequestBody() {

        IApiModel model = getModel();

        String json = JsonUtils.toString(model);

        return RequestBody.create(JSON_TYPE, json);
    }

}
