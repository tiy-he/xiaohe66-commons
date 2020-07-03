package com.xiaohe66.common.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaohe
 * @time 2020.07.03 14:14
 */
public class GsonLocalDateDeserializer implements JsonDeserializer<LocalDate> {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String datetime = json.getAsJsonPrimitive().getAsString();
        return LocalDate.parse(datetime, dateFormatter);
    }
}
