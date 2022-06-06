package com.xiaohe66.common.util.ex;

import com.xiaohe66.common.dto.R;

/**
 * 错误码根接口，外部可以通过实现该接口来扩展错误码
 *
 * @author xiaohe
 * @since 2022.06.06 13:33
 */
public interface IErrorCode {

    int getCode();

    String getMsg();

    default BusinessException toEx() {
        return new BusinessException(this);
    }

    default <T> R<T> toR() {
        return R.build(this);
    }
}
