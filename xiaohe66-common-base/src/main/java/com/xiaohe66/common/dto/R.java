package com.xiaohe66.common.dto;

import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xiaohe
 * @since 2021.08.13 14:46
 */
@Getter
@Setter
@ToString
public class R<T> {

    public static final int SUCCESS_CODE = 10000;
    public static final int ERROR_CODE = ErrorCodeEnum.ERROR.getCode();

    public static final R<?> SUCCESS = R.ok(null);
    public static final R<?> FAIL = R.err("");

    public static final R<Boolean> SUCCESS_TRUE = new R<>(SUCCESS_CODE, true, "");
    public static final R<Boolean> SUCCESS_FALSE = new R<>(SUCCESS_CODE, false, "");

    private int code;
    private String msg;
    private T data;

    public R() {

    }

    public R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    @SuppressWarnings("unchecked")
    public static <T> R<T> ok() {
        return (R<T>) SUCCESS;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS_CODE, data, "");
    }

    public static <T> R<T> ok(T data, String msg) {
        return new R<>(SUCCESS_CODE, data, msg);
    }

    public static R<Boolean> ok(boolean bool) {
        return bool ? SUCCESS_TRUE : SUCCESS_FALSE;
    }

    public static R<Boolean> okTrue() {
        return SUCCESS_TRUE;
    }

    public static R<Boolean> okFalse() {
        return SUCCESS_FALSE;
    }

    @SuppressWarnings("unchecked")
    public static <T> R<T> err() {
        return (R<T>) FAIL;
    }

    public static <T> R<T> err(String msg) {
        return new R<>(ERROR_CODE, null, msg);
    }

    public static <T> R<T> build(int code, String msg) {
        return new R<>(code, null, msg);
    }

    public static <T> R<T> build(int code, String msg, T data) {
        return new R<>(code, data, msg);
    }

    public static <T> R<T> build(ErrorCodeEnum codeEnum) {
        return new R<>(codeEnum.getCode(), null, codeEnum.getMsg());
    }

    public static <T> R<T> build(ErrorCodeEnum codeEnum, String msg) {
        return new R<>(codeEnum.getCode(), null, msg);
    }

    public static <T> R<T> build(BusinessException e) {
        return new R<>(e.getCode(), null, e.getMsg());
    }
}
