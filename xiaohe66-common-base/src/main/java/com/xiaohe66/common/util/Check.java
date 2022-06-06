package com.xiaohe66.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author xiaohe
 * @see Assert
 * @since 2019-10-28 11:07
 * @deprecated 2022.06.06, 建议使用 {@link Assert}
 */
@Deprecated
public class Check {

    private Check() {
    }

    public static boolean isNotEmpty(Object obj) {
        return obj != null;
    }

    public static boolean isNotEmpty(Object[] obj) {
        return obj != null && obj.length != 0;
    }

    public static boolean isNotEmpty(String obj) {
        return obj != null && obj.length() != 0;
    }

    public static boolean isNotEmpty(Collection<?> obj) {
        return obj != null && !obj.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> obj) {
        return obj != null && !obj.isEmpty();
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(String obj) {
        return obj == null || obj.length() == 0;
    }

    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static void notEmpty(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void notEmpty(String obj) {
        if (obj == null || obj.length() == 0) {
            threadEx();
        }
    }

    public static void notEmpty(Collection<?> obj) {
        if (obj == null || obj.isEmpty()) {
            threadEx();
        }
    }

    public static void notEmpty(Map<?, ?> obj) {
        if (obj == null || obj.isEmpty()) {
            threadEx();
        }
    }

    public static void notEmpty(Object obj, String paramName) {
        if (obj == null) {
            threadEx(paramName);
        }
    }

    public static void notEmpty(Object[] obj, String paramName) {
        if (obj == null || obj.length == 0) {
            threadEx(paramName);
        }
    }

    public static void notEmpty(String obj, String paramName) {
        if (obj == null || obj.length() == 0) {
            threadEx(paramName);
        }
    }

    public static void notEmpty(Collection<?> obj, String paramName) {
        if (obj == null || obj.isEmpty()) {
            threadEx(paramName);
        }
    }

    public static void notEmpty(Map<?, ?> obj, String paramName) {
        if (obj == null || obj.isEmpty()) {
            threadEx(paramName);
        }
    }

    private static void threadEx() {
        throw new IllegalArgumentException("参数不能为空");
    }

    private static void threadEx(String paramName) {
        throw new IllegalArgumentException("参数[" + paramName + "]不能为空");
    }
}
