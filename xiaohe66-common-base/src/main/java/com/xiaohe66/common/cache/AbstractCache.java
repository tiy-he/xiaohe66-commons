package com.xiaohe66.common.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohe
 * @since 2022.01.21 11:31
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    // TODO : 考虑是否实现 close接口

    protected static final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    protected static final long DEFAULT_CLEAR_DURATION_SECONDS = 30;

    protected final ConcurrentMap<K, Item<V>> cache = new ConcurrentHashMap<>();

    protected final long clearDurationSeconds;

    public AbstractCache() {
        this(DEFAULT_CLEAR_DURATION_SECONDS);
    }

    public AbstractCache(long clearDurationSeconds) {
        this.clearDurationSeconds = clearDurationSeconds;
        startClearTask();
    }

    protected void startClearTask() {
        scheduledExecutor.scheduleAtFixedRate(this::clearOverdueCache, clearDurationSeconds, clearDurationSeconds, TimeUnit.SECONDS);
    }

    protected abstract void clearOverdueCache();

    @Override
    public V remove(K key) {
        Item<V> item = cache.remove(key);
        if (item == null) {
            return null;
        }

        return item.getValue();
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Data
    @AllArgsConstructor
    protected static class Item<V> {

        private final V value;

    }
}
