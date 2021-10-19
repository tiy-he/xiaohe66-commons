package com.xiaohe66.common.dto;

import org.junit.Test;

import static org.junit.Assert.*;

public class RTest {

    @Test
    public void test1() {

        R<Object> ok = R.ok();
        System.out.println(ok);

        R<Object> err = R.err();
        System.out.println(err);

        R<Object> err2 = R.err("");
        System.out.println(err2);

    }
}