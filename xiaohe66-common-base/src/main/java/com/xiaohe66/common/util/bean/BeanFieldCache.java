package com.xiaohe66.common.util.bean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohe
 * @time 2020.07.20 10:49
 */
@Slf4j
public class BeanFieldCache {

    private final Cache<String, List<BeanField>> cache;

    public BeanFieldCache() {

        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .build();
    }

    public List<BeanField> get(Class<?> beanCls) {

        try {
            return cache.get(beanCls.getName(), () -> reflectFieldList(beanCls));

        } catch (ExecutionException e) {
            throw new BeanCacheException(e);
        }
    }

    private List<BeanField> reflectFieldList(Class<?> beanCls) {

        Field[] fields = beanCls.getDeclaredFields();

        if (fields.length == 0) {
            return Collections.emptyList();
        }

        List<BeanField> result = new ArrayList<>(fields.length);

        for (Field field : fields) {

            BeanFieldIgnore ignore = field.getAnnotation(BeanFieldIgnore.class);
            if (ignore != null) {
                continue;
            }

            BeanFieldName fieldName = field.getAnnotation(BeanFieldName.class);

            String name = null;
            if (fieldName != null) {
                String value = fieldName.value();
                if (StringUtils.isNotEmpty(value)) {
                    name = value;
                }
            }
            if (name == null) {
                name = field.getName();
            }

            field.setAccessible(true);

            BeanField beanField = new BeanField(field, name);
            result.add(beanField);
        }

        return result;
    }
}
