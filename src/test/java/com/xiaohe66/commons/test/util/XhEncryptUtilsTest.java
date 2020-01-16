package com.xiaohe66.commons.test.util;

import com.xiaohe66.common.util.XhEncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author xiaohe
 * @time 2020.01.16 17:49
 */
@Slf4j
public class XhEncryptUtilsTest {

    @Test
    public void test1() {

        byte[] arr = {-128, 0, 127};
        String correctEncode = "0080ff";

        String encode = XhEncryptUtils.encode(arr);

        assertEquals(correctEncode, encode);

        byte[] bytes = XhEncryptUtils.decodeToByteArr(encode);

        assertArrayEquals(arr, bytes);
    }

    @Test
    public void test2() {

        String originText = "Hello world~ 你好世界！";

        String correctEncode = "c8e5ececefa0f7eff2ece4fea0643d2065253d64381667150c6f3c01";

        String encode = XhEncryptUtils.encode(originText);

        assertEquals(correctEncode, encode);

        String decode = XhEncryptUtils.decode(encode);

        assertEquals(originText, decode);

    }
}
