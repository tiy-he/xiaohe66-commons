package com.xiaohe66.common.api.okhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaohe66.common.api.ApiException;
import com.xiaohe66.common.api.BaseApiRequest;
import com.xiaohe66.common.api.BaseApiResponse;
import com.xiaohe66.common.api.IApiModel;
import com.xiaohe66.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author xiaohe
 * @time 2021.07.21 09:46
 */
@Slf4j
public abstract class BaseOkHttpApiRequest<R extends BaseApiResponse> extends BaseApiRequest<R> {

    public BaseOkHttpApiRequest(Method method) {
        super(method);
    }

    @Override
    public RequestBody buildRequestBody() {
        IApiModel model = getModel();
        ObjectNode objectNode;
        try {
            objectNode = JsonUtils.formatObjectNode(model);

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("cannot convert jsonObject, model : " + model, e);
        }

        FormBody.Builder body = new FormBody.Builder();

        objectNode.fields().forEachRemaining(entry -> {

            JsonNode node = entry.getValue();
            if (!node.isNull()) {
                String value = node.isValueNode() ? node.asText() : node.toString();
                body.add(entry.getKey(), value);
            }
        });

        return body.build();
    }

    @Override
    public R buildResponseBody(String response) throws ApiException {

        try {
            R object = JsonUtils.formatObject(response, getResponseClass());
            object.setResponse(response);
            return object;

        } catch (JsonProcessingException e) {

            try {
                R object = getResponseClass().newInstance();
                object.setResponse(response);

                log.error("parse json fail, response : {}", response, e);

                return object;

            } catch (InstantiationException | IllegalAccessException e1) {
                throw new OkHttpApiException("cannot parse json : " + response, e);
            }
        }
    }

    /**
     * 结果类class对象
     * todo : 使用 class 不适合，无法兼容泛型情况
     *
     * @return 结果类class对象
     */
    protected abstract Class<R> getResponseClass();
}
