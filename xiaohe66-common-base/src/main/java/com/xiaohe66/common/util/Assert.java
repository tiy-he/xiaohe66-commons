package com.xiaohe66.common.util;

import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author xiaohe
 * @since 2021.12.02 17:13
 */
public class Assert {

    protected Assert() {

    }

    public static void isTrue(boolean bool) {
        if (!bool) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    public static void isFalse(boolean bool) {
        if (bool) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    public static void isNull(Object object) {
        if (object != null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void isEmpty(String object) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void isEmpty(Collection<?> object) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void isEmpty(Map<?, ?> object) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void notNull(Object object) {
        if (object == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void notEmpty(String object) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void notEmpty(Collection<?> object) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void notEmpty(Map<?, ?> object) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void lt(long value, long num) {
        if (value >= num) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    public static void gt(long value, long num) {
        if (value <= num) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    public static void isTrue(boolean bool, ErrorCodeEnum errorCode) {
        if (!bool) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isFalse(boolean bool, ErrorCodeEnum errorCode) {
        if (bool) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isNull(Object object, ErrorCodeEnum errorCode) {
        if (object != null) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(String object, ErrorCodeEnum errorCode) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(Collection<?> object, ErrorCodeEnum errorCode) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isEmpty(Map<?, ?> object, ErrorCodeEnum errorCode) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notNull(Object object, ErrorCodeEnum errorCode) {
        if (object == null) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(String object, ErrorCodeEnum errorCode) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(Collection<?> object, ErrorCodeEnum errorCode) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void notEmpty(Map<?, ?> object, ErrorCodeEnum errorCode) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void lt(long value, long num, ErrorCodeEnum errorCode) {
        if (value >= num) {
            throw new BusinessException(errorCode);
        }
    }

    public static void gt(long value, long num, ErrorCodeEnum errorCode) {
        if (value <= num) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isTrue(boolean bool, ErrorCodeEnum errorCode, String message) {
        if (!bool) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isFalse(boolean bool, ErrorCodeEnum errorCode, String message) {
        if (bool) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isNull(Object object, ErrorCodeEnum errorCode, String message) {
        if (object != null) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isEmpty(String object, ErrorCodeEnum errorCode, String message) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isEmpty(Collection<?> object, ErrorCodeEnum errorCode, String message) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isEmpty(Map<?, ?> object, ErrorCodeEnum errorCode, String message) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void notNull(Object object, ErrorCodeEnum errorCode, String message) {
        if (object == null) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void notEmpty(String object, ErrorCodeEnum errorCode, String message) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void notEmpty(Collection<?> object, ErrorCodeEnum errorCode, String message) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void notEmpty(Map<?, ?> object, ErrorCodeEnum errorCode, String message) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void lt(long value, long num, ErrorCodeEnum errorCode, String message) {
        if (value >= num) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void gt(long value, long num, ErrorCodeEnum errorCode, String message) {
        if (value <= num) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void isTrue(boolean bool, Supplier<RuntimeException> supplier) {
        if (!bool) {
            throw supplier.get();
        }
    }

    public static void isFalse(boolean bool, Supplier<RuntimeException> supplier) {
        if (bool) {
            throw supplier.get();
        }
    }

    public static void isNull(Object object, Supplier<RuntimeException> supplier) {
        if (object != null) {
            throw supplier.get();
        }
    }

    public static void isEmpty(String object, Supplier<RuntimeException> supplier) {
        if (object != null && !object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void isEmpty(Collection<?> object, Supplier<RuntimeException> supplier) {
        if (object != null && !object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void isEmpty(Map<?, ?> object, Supplier<RuntimeException> supplier) {
        if (object != null && !object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void notNull(Object object, Supplier<RuntimeException> supplier) {
        if (object == null) {
            throw supplier.get();
        }
    }

    public static void notEmpty(String object, Supplier<RuntimeException> supplier) {
        if (object == null || object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void notEmpty(Collection<?> object, Supplier<RuntimeException> supplier) {
        if (object == null || object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void notEmpty(Map<?, ?> object, Supplier<RuntimeException> supplier) {
        if (object == null || object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void lt(long value, long num, Supplier<RuntimeException> supplier) {
        if (value >= num) {
            throw supplier.get();
        }
    }

    public static void gt(long value, long num, Supplier<RuntimeException> supplier) {
        if (value <= num) {
            throw supplier.get();
        }
    }
}
