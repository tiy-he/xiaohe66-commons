package com.xiaohe66.common.net.xh;

import lombok.Data;

/**
 * @author xiaohe
 * @time 17-10-31 031
 */
@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

}
