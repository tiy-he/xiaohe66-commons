package com.xiaohe66.common.util;

import java.nio.charset.StandardCharsets;

/**
 * 自定义编码
 *
 * 每个字符(英文、数字、特殊字符)等在编码后长度变成 2，每个中文编码后变成长度 6
 *
 * @author xiaohe
 * @time 2020.01.16 17:45
 */
public class XhEncryptUtils {


    private XhEncryptUtils() {
    }


    public static String encode(String context) {
        return encode(context.getBytes(StandardCharsets.UTF_8));
    }

    public static String encode(byte[] context) {

        StringBuilder result = new StringBuilder();
        for (byte b : context) {
            String item = Integer.toHexString(b + 128);
            if (item.length() == 1) {
                result.append('0');
            }
            result.append(item);
        }

        return result.toString();

    }

    public static String decode(String context) {
        byte[] bytes = decodeToByteArr(context);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] decodeToByteArr(String context) {

        int length = context.length();

        byte[] result = new byte[length / 2];

        for (int i = 0; i < result.length; i++) {

            int startIndex = i * 2;
            int endIndex = startIndex + 2;

            String item = context.substring(startIndex, endIndex);
            int itemByte = Integer.parseInt(item, 16);

            result[i] = (byte) (itemByte - 128);
        }
        return result;
    }


}
