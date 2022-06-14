package com.xiaohe66.commons.log.context.impl;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CacheParameterNameDiscovererTest {

    @Test
    public void test1() throws NoSuchMethodException {

        Method method1 = CacheParameterNameDiscovererTest.class.getMethod("testFunction", String.class, Integer.TYPE);

        Method method2 = CacheParameterNameDiscovererTest.class.getMethod("testFunction", String.class, Integer.TYPE);

        // 只有重写了 equals 方法才能使用 HashMap
        assertEquals(method1, method2);

        String[] arr1 = CacheParameterNameDiscoverer.getParameterNames(method1);
        String[] arr2 = CacheParameterNameDiscoverer.getParameterNames(method1);

        assertArrayEquals(arr1,arr2);
    }

    public String testFunction(String s1, int i1) {
        return null;
    }
}
