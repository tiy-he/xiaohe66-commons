package com.xiaohe66.common.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JoinUtilsTest {


    @Test
    public void test1() {

        List<String> list = Arrays.asList("1", "2", "3");

        String correctString = "1,2,3";

        String joinStr = JoinUtils.join(list);

        assertEquals(correctString, joinStr);

    }

    @Test
    public void test2() {

        List<Integer> list = Arrays.asList(1,2,3,4);

        String correctString = "1,2,3,4";

        String joinStr = JoinUtils.join(list);

        assertEquals(correctString, joinStr);

    }

    @Test
    public void test3() {

        List<Integer> list = Collections.emptyList();

        String correctString = "";

        String joinStr = JoinUtils.join(list);

        assertEquals(correctString, joinStr);

    }

    @Test
    public void test4() {

        List<Integer> list = Arrays.asList(1,2,3,4);

        String correctString = "1|2|3|4";

        String joinStr = JoinUtils.join(list,"|");

        assertEquals(correctString, joinStr);

    }
}