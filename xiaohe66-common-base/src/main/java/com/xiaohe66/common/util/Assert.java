package com.xiaohe66.common.util;

import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;
import com.xiaohe66.common.util.ex.IErrorCode;

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

    public static void requireTrue(boolean bool) {
        if (!bool) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    public static void requireFalse(boolean bool) {
        if (bool) {
            throw new BusinessException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    public static void requireNull(Object object) {
        if (object != null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireEmpty(String object) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireEmpty(Collection<?> object) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireEmpty(Map<?, ?> object) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireNotNull(Object object) {
        if (object == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireNotEmpty(String object) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireNotEmpty(Collection<?> object) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_EMPTY);
        }
    }

    public static void requireNotEmpty(Map<?, ?> object) {
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

    public static void requireTrue(boolean bool, IErrorCode errorCode) {
        if (!bool) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireFalse(boolean bool, IErrorCode errorCode) {
        if (bool) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireNull(Object object, IErrorCode errorCode) {
        if (object != null) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireEmpty(String object, IErrorCode errorCode) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireEmpty(Collection<?> object, IErrorCode errorCode) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireEmpty(Map<?, ?> object, IErrorCode errorCode) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireNotNull(Object object, IErrorCode errorCode) {
        if (object == null) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireNotEmpty(String object, IErrorCode errorCode) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireNotEmpty(Collection<?> object, IErrorCode errorCode) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireNotEmpty(Map<?, ?> object, IErrorCode errorCode) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    public static void lt(long value, long num, IErrorCode errorCode) {
        if (value >= num) {
            throw new BusinessException(errorCode);
        }
    }

    public static void gt(long value, long num, IErrorCode errorCode) {
        if (value <= num) {
            throw new BusinessException(errorCode);
        }
    }

    public static void requireTrue(boolean bool, IErrorCode errorCode, String message) {
        if (!bool) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireFalse(boolean bool, IErrorCode errorCode, String message) {
        if (bool) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireNull(Object object, IErrorCode errorCode, String message) {
        if (object != null) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireEmpty(String object, IErrorCode errorCode, String message) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireEmpty(Collection<?> object, IErrorCode errorCode, String message) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireEmpty(Map<?, ?> object, IErrorCode errorCode, String message) {
        if (object != null && !object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireNotNull(Object object, IErrorCode errorCode, String message) {
        if (object == null) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireNotEmpty(String object, IErrorCode errorCode, String message) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireNotEmpty(Collection<?> object, IErrorCode errorCode, String message) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireNotEmpty(Map<?, ?> object, IErrorCode errorCode, String message) {
        if (object == null || object.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void lt(long value, long num, IErrorCode errorCode, String message) {
        if (value >= num) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void gt(long value, long num, IErrorCode errorCode, String message) {
        if (value <= num) {
            throw new BusinessException(errorCode, message);
        }
    }

    public static void requireTrue(boolean bool, Supplier<RuntimeException> supplier) {
        if (!bool) {
            throw supplier.get();
        }
    }

    public static void requireFalse(boolean bool, Supplier<RuntimeException> supplier) {
        if (bool) {
            throw supplier.get();
        }
    }

    public static void requireNull(Object object, Supplier<RuntimeException> supplier) {
        if (object != null) {
            throw supplier.get();
        }
    }

    public static void requireEmpty(String object, Supplier<RuntimeException> supplier) {
        if (object != null && !object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void requireEmpty(Collection<?> object, Supplier<RuntimeException> supplier) {
        if (object != null && !object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void requireEmpty(Map<?, ?> object, Supplier<RuntimeException> supplier) {
        if (object != null && !object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void requireNotNull(Object object, Supplier<RuntimeException> supplier) {
        if (object == null) {
            throw supplier.get();
        }
    }

    public static void requireNotEmpty(String object, Supplier<RuntimeException> supplier) {
        if (object == null || object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void requireNotEmpty(Collection<?> object, Supplier<RuntimeException> supplier) {
        if (object == null || object.isEmpty()) {
            throw supplier.get();
        }
    }

    public static void requireNotEmpty(Map<?, ?> object, Supplier<RuntimeException> supplier) {
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
