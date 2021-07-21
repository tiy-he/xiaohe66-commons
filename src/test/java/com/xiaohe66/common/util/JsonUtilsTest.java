package com.xiaohe66.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Slf4j
public class JsonUtilsTest {

    private User originUser;

    @Before
    public void Before() {
        originUser = new User();
        originUser.id = 100;
        originUser.name = "张三";
    }

    @Test
    public void test1() throws JsonProcessingException {

        LocalDateTime now = LocalDateTime.now();

        originUser.date = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());

        String json = JsonUtils.toJson(originUser);

        User user = JsonUtils.formatObject(json, User.class);

        log.info("json : {}", json);

        assertEquals(originUser, user);

        List<User> list = new ArrayList<>();
        list.add(this.originUser);
        list.add(this.originUser);

        String listJson = JsonUtils.toJson(list);

        assertEquals(list, JsonUtils.formatListObject(listJson, User.class));

    }

    @Test
    public void testFormatObject1() throws JsonProcessingException {

        List<User> list = new ArrayList<>();
        list.add(this.originUser);
        list.add(this.originUser);

        String listJson = JsonUtils.toJson(list);

        List<User> retList = JsonUtils.formatObject(listJson, new TypeReference<List<User>>() {
        });

        assertEquals(list, retList);

    }

    @Test
    public void testFormatObject2() throws JsonProcessingException {

        String json = "{\"id\" :  \"100\", \"name\" :  \"张三\"}";

        User retUser = JsonUtils.formatObject(json, User.class);

        assertEquals(originUser, retUser);
    }

    @Test
    public void testFormatObject3() throws JsonProcessingException {

        Result<User> originObject = new Result<>();
        originObject.setData(originUser);
        originObject.setCode(50);

        String originJson = JsonUtils.toJson(originObject);

        log.info("originJson : {}", originJson);

        Result<String> retObject = JsonUtils.formatObject(originJson, new TypeReference<Result<String>>() {
        });

        Result<String> correctResult = new Result<>();
        correctResult.setCode(50);
        correctResult.setData(JsonUtils.toJson(originUser));

        assertEquals(correctResult, retObject);
    }

    @Test
    public void testFormatObject4() throws JsonProcessingException {

        List<User> userList = Collections.singletonList(originUser);

        Result<List<User>> originObject = new Result<>();
        originObject.setCode(50);
        originObject.setData(userList);

        String originJson = JsonUtils.toJson(originObject);

        log.info("originJson : {}", originJson);

        Result<String> retObject = JsonUtils.formatObject(originJson, new TypeReference<Result<String>>() {
        });

        Result<String> correctResult = new Result<>();
        correctResult.setCode(50);
        correctResult.setData(JsonUtils.toJson(userList));

        assertEquals(correctResult, retObject);
    }

    @Test
    public void test5() throws JsonProcessingException {

        ObjectNode jsonNodes = JsonUtils.formatObjectNode(originUser);

        jsonNodes.fields().forEachRemaining(entry -> {

            if (!entry.getValue().isNull()) {

                log.info("{} : {}", entry.getKey(), entry.getValue().asText());
            }
        });

    }

    @Data
    @EqualsAndHashCode
    private static class User {

        private Integer id;
        private String name;
        private LocalDateTime date;

    }

    @Data
    @EqualsAndHashCode
    private static class Result<T> {

        private Integer code;
        private T data;

    }
}