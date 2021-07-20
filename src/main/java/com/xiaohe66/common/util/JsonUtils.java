package com.xiaohe66.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * jackson 是使用实体类的 get/set 进行取值和设值的，因此若实体类没有对应的 get/set 方法则会报错。
 * jackson 的属性名称 = 去掉get、set后再把第1个大写字母变成小写。
 *
 * @author xiaohe
 * @time 2021.07.19 15:07
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {

    }

    static {
        // 忽略未知的JSON字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //不允许基本类型为null
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        // 如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule module = new SimpleModule();

        // String 容错，反序列化时，可以将 Array 或 Object 转成 String
        module.addDeserializer(String.class, new JsonDeserializer<String>() {
            @Override
            public String deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {

                TreeNode treeNode = parser.getCodec().readTree(parser);

                return treeNode.isValueNode() ?
                        ((TextNode) treeNode).asText() :
                        treeNode.toString();
            }
        });

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        objectMapper.registerModule(module);
        objectMapper.registerModule(javaTimeModule);

    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {

            // 当传入的类没有实现 get 方法时，便会抛出该异常
            throw new IllegalStateException("toJson error", e);
        }
    }

    public static <T> T formatObject(String json, Class<T> cls) throws JsonProcessingException {
        return objectMapper.readValue(json, cls);
    }

    public static <T> T formatObject(String json, JavaType javaType) throws JsonProcessingException {
        return objectMapper.readValue(json, javaType);
    }

    public static <T> T formatObject(String json, TypeReference<T> javaType) throws JsonProcessingException {
        return objectMapper.readValue(json, javaType);
    }

    public static <T> List<T> formatListObject(String json, Class<T> cls) throws JsonProcessingException {

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, cls);

        return objectMapper.readValue(json, javaType);
    }


}
