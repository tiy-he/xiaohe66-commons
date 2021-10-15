package com.xiaohe66.common.util;

import com.xiaohe66.common.util.bean.BeanFieldIgnore;
import com.xiaohe66.common.util.bean.BeanFieldName;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BeanUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(BeanUtilsTest.class);

    @EqualsAndHashCode
    @ToString
    public static class TestObj {

        private String name;

        private int code;

        private String data;

        @BeanFieldIgnore
        private String ignore;

        @BeanFieldName("newName")
        private String rename;

    }

    @Test
    public void test1() {

        TestObj testObj = new TestObj();
        testObj.name = "field name";
        testObj.code = 100;
        testObj.data = "field data";
        testObj.ignore = "field ignore";
        testObj.rename = "field rename";

        Map<String, Object> correctMap = new HashMap<>();
        correctMap.put("code", 100);
        correctMap.put("newName", "field rename");
        correctMap.put("data", "field data");
        correctMap.put("name", "field name");

        log.info("first toMap");
        Map<String, Object> result = BeanUtils.toMap(testObj);
        log.info("BeanUtils toMap result : {}", result);
        assertEquals(correctMap, result);

        testObj.data = "new Data";
        correctMap.put("data", "new Data");
        log.info("second toMap");
        result = BeanUtils.toMap(testObj);
        log.info("BeanUtils toMap result : {}", result);
        assertEquals(correctMap, result);
    }

    @Test
    public void test2() {

        TestObj correctObject = new TestObj();
        correctObject.name = "field name";
        correctObject.code = 100;
        correctObject.data = "field data";
        correctObject.rename = "field rename";

        Map<String, Object> map = new HashMap<>();
        map.put("code", 100);
        map.put("newName", "field rename");
        map.put("data", "field data");
        map.put("name", "field name");

        TestObj object = BeanUtils.mapToBean(map, TestObj.class);

        assertEquals(correctObject, object);

    }
}