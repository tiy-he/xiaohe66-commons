package com.xiaohe66.common.web.mybatis.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

/**
 * @author xiaohe
 * @since 2021.08.12 10:05
 */
public abstract class BaseServiceSupport<M extends IBaseMapper<T>, T extends IBaseEntity>
        extends ServiceImpl<M, T> {

    public boolean isExist(Serializable id) {
        return baseMapper.isExistId(id);
    }

    /**
     * 判断字段是否存在指定值
     *
     * @param fieldName 字段名，必须由系统指定，不允许前端传入，存在sql注入风险
     * @param value     字段的值，可以前端传入
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist(String fieldName, Object value) {
        return baseMapper.isExist(fieldName, value);
    }

}
