package com.xiaohe66.common.cache.impl;

import com.xiaohe66.common.cache.AbstractCache;
import com.xiaohe66.common.util.SystemClock;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author xiaohe
 * @since 2022.01.21 17:02
 */
@Slf4j
public class SimpleCache<K, V> extends AbstractCache<K, V> {

    protected final long expireAfterWriteMs;
    protected final long expireAfterReadMs;

    public SimpleCache(long expireAfterWriteMs, long expireAfterReadMs) {
        this.expireAfterWriteMs = expireAfterWriteMs;
        this.expireAfterReadMs = expireAfterReadMs;
    }

    @Override
    public void put(K key, V value) {

        long currentTime = SystemClock.currentTimeMillis();

        long expireWriteTimestamp = expireAfterWriteMs > 0 ? currentTime + expireAfterWriteMs : Long.MAX_VALUE;
        long expireReadTimestamp = expireAfterReadMs > 0 ? currentTime + expireAfterReadMs : Long.MAX_VALUE;

        Item<V> item = new Item<>(value, expireWriteTimestamp, expireReadTimestamp);

        cache.put(key, item);
    }

    @Override
    public V get(K key) {

        Item<V> item = (Item<V>) cache.get(key);

        if (item == null) {
            return null;
        }

        long currentTime = SystemClock.currentTimeMillis();

        boolean isExpire = (expireAfterWriteMs > 0 && item.getExpireWriteTimestamp() < currentTime) ||
                (expireAfterReadMs > 0 && item.getExpireReadTimestamp() < currentTime);

        if (isExpire) {

            log.debug("remove cache : {}", key);

            // note : 若在此处移除时之前，插入了一个相同的key，则这个key会被移除。这就产生了并发问题
            cache.remove(key);
            return null;

        } else {

            log.debug("increase cache time : {}", key);

            item.setExpireReadTimestamp(currentTime + expireAfterReadMs);
            return item.getValue();
        }
    }


    @Override
    protected void startClearTask() {
        if (expireAfterReadMs > 0 || expireAfterWriteMs > 0) {
            super.startClearTask();
        }
    }

    @Override
    protected void clearOverdueCache() {

        log.debug("start clearOverdueCache");

        long currentTime = SystemClock.currentTimeMillis();

        for (Map.Entry<K, AbstractCache.Item<V>> entry : cache.entrySet()) {

            Item<V> item = (Item<V>) entry.getValue();
            if (item.getExpireWriteTimestamp() < currentTime || item.getExpireReadTimestamp() < currentTime) {

                log.debug("remove cache : {}", entry.getKey());

                cache.remove(entry.getKey());
            }
        }
        log.debug("end clearOverdueCache");
    }

    @ToString(callSuper = true)
    @Getter
    protected static class Item<V> extends AbstractCache.Item<V> {

        protected final long expireWriteTimestamp;

        @Setter
        protected long expireReadTimestamp;

        public Item(V value, long expireWriteTimestamp, long expireReadTimestamp) {
            super(value);
            this.expireWriteTimestamp = expireWriteTimestamp;
            this.expireReadTimestamp = expireReadTimestamp;
        }
    }
}
