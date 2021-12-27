package com.xiaohe66.common.api.okhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaohe66.common.api.IApiModel;
import com.xiaohe66.common.api.IApiResponse;
import com.xiaohe66.common.util.JsonUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author xiaohe
 * @since 2021.12.27 15:32
 */
public abstract class FormOkHttpApiRequest<R extends IApiResponse> extends BaseOkHttpApiRequest<R> {

    public FormOkHttpApiRequest(Method method) {
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
}
