package com.xiaohe66.commons.log.context.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohe
 * @since 2022.06.09 11:36
 */
@Slf4j
public class CacheParameterNameDiscoverer {

    private static final Map<Method, String[]> cache = new ConcurrentHashMap<>();

    private CacheParameterNameDiscoverer() {

    }

    public static String[] getParameterNames(Method method) {

        return cache.computeIfAbsent(method, (k) -> {
            log.info("getParameterNames : {}", method);
            ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
            return pnd.getParameterNames(method);
        });
    }
}
