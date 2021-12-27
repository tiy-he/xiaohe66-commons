package com.xiaohe66.common.util;

import com.fasterxml.jackson.databind.JavaType;
import com.xiaohe66.common.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class JsonUtilsConstructTypeTest {

    @Test
    public void test1() {

        JavaType javaType = JsonUtils.constructType(R.class, String.class);

        Class<?> rawClass = javaType.getRawClass();

        log.info("javaType 一级类型 : {}", rawClass.getName());

        assertEquals(R.class, rawClass);
    }
}