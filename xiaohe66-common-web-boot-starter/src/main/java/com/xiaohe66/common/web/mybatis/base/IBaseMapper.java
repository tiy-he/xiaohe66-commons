package com.xiaohe66.common.web.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * @author xiaohe
 * @since 2021.08.11 18:34
 */
public interface IBaseMapper<T extends IBaseEntity> extends BaseMapper<T> {

    boolean isExistId(Serializable id);

    /**
     * 判断字段是否存在指定值
     *
     * @param fieldName 字段名，必须由系统指定，不允许前端传入，存在sql注入风险
     * @param value     字段的值，可以前端传入
     * @return 存在返回true，不存在返回false
     */
    boolean isExist(String fieldName, Object value);

}