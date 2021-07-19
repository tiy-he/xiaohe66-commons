package com.xiaohe66.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaohe66.common.util.gson.GsonLocalDateDeserializer;
import com.xiaohe66.common.util.gson.GsonLocalDateTimeDeserializer;
import com.xiaohe66.common.util.gson.GsonResultDeserializer;
import com.xiaohe66.common.util.gson.GsonStringDeserializer;
import com.xiaohe66.common.net.xh.Result;
import com.xiaohe66.common.reflect.ParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class GsonUtils {

    private static final Logger log = LoggerFactory.getLogger(GsonUtils.class);

    private static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(String.class, new GsonStringDeserializer())
                .registerTypeAdapter(Result.class, new GsonResultDeserializer());

        try {
            // 安卓26版本（安卓8.0）以下无法使用 LocalDate
            Class.forName("java.time.LocalDate");
            builder.registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeDeserializer());
            builder.registerTypeAdapter(LocalDate.class, new GsonLocalDateDeserializer());

        } catch (ClassNotFoundException e) {
            log.error("无法加载LocalDate类");
        }

        gson = builder.create();
    }

    private GsonUtils() {
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

    public static <T> List<T> formatList(String jsonStr, Class<T> cls) {
        Type type = new ParamType(List.class, cls);
        return gson.fromJson(jsonStr, type);
    }

    public static <T> List<T> formatList(String jsonStr, Type type) {
        Type listType = new ParamType(List.class, type);
        return gson.fromJson(jsonStr, listType);
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
