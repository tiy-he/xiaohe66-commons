package com.xiaohe66.common.cache;

import com.xiaohe66.common.cache.impl.SimpleCache;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaohe
 * @since 2022.01.21 11:33
 */
public class CacheBuilder {

    private long expireAfterReadMs;
    private long expireAfterWriteMs;

    protected CacheBuilder() {

    }

    public static CacheBuilder newBuilder() {
        return new CacheBuilder();
    }

    public CacheBuilder expireAfterRead(long duration, TimeUnit unit) {
        this.expireAfterReadMs = TimeUnit.MILLISECONDS.convert(duration, unit);
        return this;
    }

    public CacheBuilder expireAfterWrite(long duration, TimeUnit unit) {
        this.expireAfterWriteMs = TimeUnit.MILLISECONDS.convert(duration, unit);
        return this;
    }

    public <K, V> Cache<K, V> build() {
        return new SimpleCache<K, V>(expireAfterWriteMs, expireAfterReadMs);
    }

}
