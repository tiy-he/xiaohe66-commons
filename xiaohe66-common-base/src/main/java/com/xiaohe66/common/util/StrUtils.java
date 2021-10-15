package com.xiaohe66.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * String 工具类
 *
 * @author xiaohe
 * @time 17-10-29 029
 */
@Slf4j
public class StrUtils {

    private StrUtils() {
    }

    /**
     * 判断2个字符串是否相同，并且不为null。
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 2个字符串不为null，并且相同时，返回true，否则返回false。
     */
    public static boolean equalsAndNotEmpty(String str1, String str2) {
        return str1 != null && str1.length() != 0 && str1.equals(str2);
    }

    /**
     * 返回string的int形式，若转换失败，则返回null
     *
     * @param val 待转换的值
     * @return 转换后的值，若转换失败，则返回null
     */
    public static int toIntNotException(String val) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            log.error("转换为int失败，待转换的值 : {}", val);
            return 0;
        }
    }

    /**
     * 返回string的int形式。若转换失败，会抛出异常。
     *
     * @param val 待转换的值
     * @return 传入参数的int形式
     * @throws IllegalArgumentException 转换失败时，抛出此异常
     */
    public static int toInt(String val) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 去除字符串前面和后面的空格，当传入的字符串为null时，返回空
     * <p>
     *
     * @param str 待清除空格的字符串
     * @return 返回清除前后空格后的字符串，当传入的字符串为null时，返回空。
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }
}
