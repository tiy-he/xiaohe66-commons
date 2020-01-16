package com.xiaohe66.common.util;

import java.util.Base64;

/**
 * @author xiaohe
 * @time 2020.01.16 15:46
 */
public class Base64Utils {

    private static final Base64.Encoder encoder = Base64.getEncoder();

    private static final Base64.Decoder decoder = Base64.getDecoder();

    private Base64Utils() {
    }

    public static String encode(byte[] arr) {
        return encoder.encodeToString(arr);
    }

    public static String encode(String str) {
        return encode(str.getBytes());
    }

    public static String decode(byte[] arr) {
        return new String(decodeToByteArr(arr));
    }

    public static String decode(String str) {
        return new String(decodeToByteArr(str));
    }

    public static byte[] decodeToByteArr(byte[] arr) {
        return decoder.decode(arr);
    }

    public static byte[] decodeToByteArr(String str) {
        return decoder.decode(str);
    }


}
