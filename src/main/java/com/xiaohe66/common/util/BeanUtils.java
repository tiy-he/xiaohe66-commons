package com.xiaohe66.common.util;

import com.xiaohe66.common.bean.Ignore;
import com.xiaohe66.common.bean.Rename;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author xiaohe
 * @time 2020.07.19 19:03
 */
public class BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {

    }

    public static Map<String, Object> toMap(Object bean) {

        Objects.requireNonNull(bean);

        Class<?> beanCls = bean.getClass();

        Field[] fields = beanCls.getDeclaredFields();

        if (fields.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new HashMap<>();


        for (Field field : fields) {

            // todo : 效率提升
            Ignore ignore = field.getDeclaredAnnotation(Ignore.class);
            if (ignore == null) {

                String name = getFieldName(field);

                Object val;
                try {
                    val = getFieldValue(field, bean);
                } catch (IllegalAccessException e) {
                    log.error("get Field value error, className : {}, fieldName : {}", beanCls.getName(), name);
                    continue;
                }

                result.put(name, val);
            }
        }

        return result;
    }

    public static void eachField(Object bean, BiConsumer<String, Object> consumer) {

        Objects.requireNonNull(bean);

        Class<?> beanCls = bean.getClass();

        Field[] fields = beanCls.getDeclaredFields();

        if (fields.length == 0) {
            return;
        }


        for (Field field : fields) {

            // todo : 效率提升
            Ignore ignore = field.getDeclaredAnnotation(Ignore.class);
            if (ignore == null) {

                String name = getFieldName(field);

                Object val;
                try {
                    val = getFieldValue(field, bean);
                } catch (IllegalAccessException e) {
                    log.error("get Field value error, className : {}, fieldName : {}", beanCls.getName(), name);
                    continue;
                }

                consumer.accept(name, val);
            }
        }
    }

    private static String getFieldName(Field field) {

        // todo : 效率提升
        Rename rename = field.getDeclaredAnnotation(Rename.class);

        String name = null;
        if (rename != null) {
            String value = rename.value();
            if (StringUtils.isNotEmpty(value)) {
                name = value;
            }
        }
        if (name == null) {
            name = field.getName();
        }

        return name;
    }

    private static Object getFieldValue(Field field, Object bean) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(bean);
    }

}
