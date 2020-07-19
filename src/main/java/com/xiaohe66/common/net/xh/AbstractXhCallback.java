package com.xiaohe66.common.net.xh;

import com.xiaohe66.common.net.AbstractCallback;
import com.xiaohe66.common.net.ResponseException;
import com.xiaohe66.common.reflect.ParamType;
import okhttp3.Call;

import java.lang.reflect.Type;

public abstract class AbstractXhCallback<T> extends AbstractCallback<Result<T>> {

    @Override
    protected void setBeanType(Type beanType) {
        super.setBeanType(new ParamType(Result.class, beanType));
    }

    @Override
    public void onSuccess(Call call, Result<T> result) {

        if (result.getCode() == 100) {

            onSuccess(result.getData());

        } else {

            // todo : 未登录、没有权限异常处理
            onFail(call, new ResponseException("接口响应码不是成功"));
        }
    }

    @Override
    public void onFail(Call call, ResponseException e) {

    }

    public abstract void onSuccess(T bean);


}
