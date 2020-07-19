package com.xiaohe66.common.util;

import com.xiaohe66.common.bean.Ignore;
import com.xiaohe66.common.bean.Rename;
import com.xiaohe66.common.net.xh.Result;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.*;

public class BeanUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(BeanUtilsTest.class);

    private static class TestObj{

        private String name;

        private int code;

        private String data;

        @Ignore
        private String ignore;

        @Rename("newName")
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

        Map<String,Object> correctMap = new HashedMap<>();
        correctMap.put("code",100);
        correctMap.put("newName","field rename");
        correctMap.put("data","field data");
        correctMap.put("name","field name");

        Map<String, Object> result = BeanUtils.toMap(testObj);

        log.info("BeanUtils toMap result : {}",result);

        assertEquals(correctMap,result);

    }
}