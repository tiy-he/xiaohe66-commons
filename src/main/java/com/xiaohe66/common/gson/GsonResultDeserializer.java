package com.xiaohe66.common.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.xiaohe66.common.net.xh.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.07.03 14:07
 */
public class GsonResultDeserializer implements JsonDeserializer<Result<?>> {

    private static final Logger log = LoggerFactory.getLogger(GsonResultDeserializer.class);

    @Override
    public Result deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException();
        }

        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement dataFeild = jsonObject.get("data");

        ParameterizedType beanType = (ParameterizedType) type;
        Type dataType = beanType.getActualTypeArguments()[0];

        Object data;
        try {
            data = context.deserialize(dataFeild, dataType);

        } catch (JsonParseException e) {
            log.error("json解析失败", e);
            data = null;
        }

        JsonElement msgField = jsonObject.get("msg");
        String msg = msgField == null ? null : msgField.getAsString();

        JsonElement resultCodeField = jsonObject.get("code");
        int code = resultCodeField == null ? -1 : resultCodeField.getAsInt();

        Result<Object> jdResult = new Result<>();
        jdResult.setMsg(msg);
        jdResult.setCode(code);
        jdResult.setData(data);

        return jdResult;
    }
}
