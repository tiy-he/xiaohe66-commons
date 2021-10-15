package com.xiaohe66.common.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 *
 * @author xiaohe
 * @time 2020.07.03 14:09
 */
public class GsonStringDeserializer implements JsonDeserializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject() || json.isJsonArray()) {
            return json.toString();

        } else if (json.isJsonNull()) {
            return "";

        } else {
            return json.getAsString();
        }
    }
}
