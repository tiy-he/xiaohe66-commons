package com.xiaohe66.common.cache;

import com.xiaohe66.common.cache.impl.SimpleCache;

/**
 * @author xiaohe
 * @since 2022.01.21 11:19
 */
public final class Caches {

    // TODO : 未完成 cache 开发，因此未开放使用

    public static final Cache<Object, Object> expireAfterWriteFiveMinute = new SimpleCache<>(5 * 60 * 1000, 0);

    private Caches() {

    }
}
