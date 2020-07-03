package com.xiaohe66.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaohe66.common.gson.GsonLocalDateDeserializer;
import com.xiaohe66.common.gson.GsonLocalDateTimeDeserializer;
import com.xiaohe66.common.gson.GsonResultDeserializer;
import com.xiaohe66.common.gson.GsonStringDeserializer;
import com.xiaohe66.common.model.Result;
import com.xiaohe66.common.reflect.ParamType;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据格式处理类
 *
 * @author xiaohe
 * @time 2019-10-29 18:13
 */
public class JsonUtils {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeDeserializer())
                .registerTypeAdapter(LocalDate.class, new GsonLocalDateDeserializer())
                .registerTypeAdapter(String.class, new GsonStringDeserializer())
                .registerTypeAdapter(Result.class, new GsonResultDeserializer())
                .create();
    }

    private JsonUtils() {
    }

    /**
     * 对象转json字符串
     *
     * @param obj 待处理java的对象
     * @return 对象对应的json字符串
     */
    public static String toString(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * json字符串转json对象
     *
     * @param jsonStr 标准json字符串
     * @return JSONObject
     */
    public static JsonObject toObject(String jsonStr) {
        return JsonParser.parseString(jsonStr).getAsJsonObject();
    }

    /**
     * json字符串转json数组对象
     *
     * @param jsonStr 标准json字符串
     * @return JSONArray
     */
    public static JsonArray toArray(String jsonStr) {
        return JsonParser.parseString(jsonStr).getAsJsonArray();
    }

    public static <T> T formatObject(String jsonStr, Class<T> cls) {
        return gson.fromJson(jsonStr, cls);
    }

    public static <T> T formatObject(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

    public static <T> Result<T> formatResult(String reader, Class<T> cls) {
        Type type = new ParamType(Result.class, cls);
        return gson.fromJson(reader, type);
    }

    public static <T> Result<T> formatResult(String reader, Type type) {
        Type resultType = new ParamType(Result.class, type);
        return gson.fromJson(reader, resultType);
    }

    public static <T> Result<List<T>> formatResultList(String reader, Class<T> cls) {
        Type listType = new ParamType(List.class, cls);
        Type type = new ParamType(Result.class, listType);
        return gson.fromJson(reader, type);
    }
}
