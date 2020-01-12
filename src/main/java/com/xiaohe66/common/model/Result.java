package com.xiaohe66.common.model;

import lombok.Getter;

/**
 * @author xiaohe
 * @time 17-10-31 031
 */
@Getter
public class Result<T> {

    private int code;
    private String msg;
    private T data;

}
