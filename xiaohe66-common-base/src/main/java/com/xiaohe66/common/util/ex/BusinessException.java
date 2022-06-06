package com.xiaohe66.common.util.ex;

import com.xiaohe66.common.dto.R;
import lombok.Getter;

/**
 * @author xiaohe
 * @since 2021.11.08 14:05
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;
    private final String msg;

    public BusinessException(IErrorCode codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public BusinessException(IErrorCode codeEnum, String msg) {
        this.code = codeEnum.getCode();
        this.msg = msg;
    }

    public BusinessException(String msg) {
        this.code = R.ERROR_CODE;
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public <T> R<T> toR() {
        return R.build(this);
    }

}
