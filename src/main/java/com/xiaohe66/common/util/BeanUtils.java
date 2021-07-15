package com.xiaohe66.common.util;

import com.xiaohe66.common.bean.BeanField;
import com.xiaohe66.common.bean.BeanFieldCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author xiaohe
 * @time 2020.07.19 19:03
 */
public class BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    private static final BeanFieldCache cache = new BeanFieldCache();

    private BeanUtils() {
    }

    public static Map<String, Object> toMap(Object bean) {

        Objects.requireNonNull(bean);

        Class<?> beanCls = bean.getClass();
        List<BeanField> beanFieldList = cache.get(beanCls);

        if (beanFieldList.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new HashMap<>();

        for (BeanField beanField : beanFieldList) {

            Field field = beanField.getField();

            Object val;
            try {
                val = field.get(bean);
            } catch (IllegalAccessException e) {
                log.error("get Field value error, className : {}, fieldName : {}", beanCls.getName(), field.getName());
                continue;
            }

            result.put(beanField.getName(), val);
        }

        return result;
    }

    public static void eachField(Object bean, BiConsumer<String, Object> consumer) {

        Objects.requireNonNull(bean);

        Class<?> beanCls = bean.getClass();

        List<BeanField> beanFieldList = cache.get(beanCls);

        if (beanFieldList.isEmpty()) {
            return;
        }

        for (BeanField beanField : beanFieldList) {

            Field field = beanField.getField();

            Object val;
            try {
                val = field.get(bean);
            } catch (IllegalAccessException e) {
                log.error("get Field value error, className : {}, fieldName : {}", beanCls.getName(), field.getName());
                continue;
            }

            consumer.accept(beanField.getName(), val);
        }
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> cls) {

        // 使用 apche 的 commons 包
        //T object = cls.newInstance();
        //org.apache.commons.beanutils.BeanUtils.populate(object,map)

        throw new UnsupportedOperationException();
    }

}
