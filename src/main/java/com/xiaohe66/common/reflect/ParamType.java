package com.xiaohe66.common.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author xiaohe
 * @time 2020.01.13 11:10
 */
public class ParamType implements ParameterizedType {
    private final Class<?> raw;
    private final Type[] args;

    public ParamType(Class<?> raw) {
        this.raw = raw;
        this.args = new Type[0];
    }

    public ParamType(Class<?> raw, Type... args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

}
