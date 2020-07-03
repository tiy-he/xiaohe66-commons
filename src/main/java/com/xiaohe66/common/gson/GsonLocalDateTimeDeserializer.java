package com.xiaohe66.common.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaohe
 * @time 2020.07.03 14:11
 */
public class GsonLocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String datetime = json.getAsJsonPrimitive().getAsString();
        return LocalDateTime.parse(datetime, dateTimeFormatter);
    }
}
