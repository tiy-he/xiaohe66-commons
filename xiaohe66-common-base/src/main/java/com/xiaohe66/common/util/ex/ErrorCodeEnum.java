package com.xiaohe66.common.util.ex;

import com.xiaohe66.common.dto.R;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author xiaohe
 * @since 2021.11.12 15:05
 */
@AllArgsConstructor
@Getter
@ToString
public enum ErrorCodeEnum {

    /**
     * 枚举
     * 40*** : 客户异常
     * 50*** : 服务异常
     * <p>
     * 原则上以 http code + 编号组成错误码
     */
    PARAM_ERROR(40000, "参数错误"),
    PARAM_EMPTY(40010, "参数为空"),
    PARAM_ILLEGAL(40020, "非法参数"),
    PARAM_ILLEGAL_REPETITION(40021, "存在重复数据"),

    /**
     * 权限 - 401
     * 401-0* = 登录相关
     * 401-1* = 功能权限
     * 401-2* = 数据权限
     * 401-3* = 其它
     */
    NOT_LOGIN(40100, "未登录"),
    NOT_FOUND_ACCOUNT(40101, "账户不存在"),
    INVALID_TOKEN(40102, "无效的令牌"),
    NOT_FUNCTION_PERMISSION(40110, "无操作权限"),
    NOT_DATA_PERMISSION(40120, "无操作权限"),
    ILLEGAL_OPERATE(40130, "非法操作"),

    /**
     * 资源未找到
     * 404-1* = 接口不存在
     * 404-2* = 数据不存在
     */
    NOT_FOUND(40400, "对象不存在"),
    NOT_FOUND_URL(40410, "接口不支持"),
    NOT_FOUND_DATA(40420, "对象不存在"),

    ERROR(50000, "系统繁忙");

    private final int code;
    private final String msg;

    public BusinessException toEx() {
        return new BusinessException(this);
    }

    public <T> R<T> toR() {
        return R.build(this);
    }

}
