package com.xiaohe66.common.dto;

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
    public static final int ERROR_CODE = -1;

    public static final R<Void> SUCCESS = R.ok(null);
    public static final R<Void> FAIL = R.err("");

    private int code;
    private String msg;
    private T data;

    public R(){

    }

    public R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    public static R<Void> ok() {
        return SUCCESS;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(SUCCESS_CODE, data, "");
    }

    public static <T> R<T> ok(T data, String msg) {
        return new R<>(SUCCESS_CODE, data, msg);
    }

    public static R<Void> err() {
        return FAIL;
    }

    public static R<Void> err(String msg) {
        return new R<>(ERROR_CODE, null, msg);
    }

    public static R<Void> build(int code, String msg) {
        return new R<>(code, null, msg);
    }

    public static <T> R<T> build(int code, String msg, T data) {
        return new R<>(code, data, msg);
    }
}
