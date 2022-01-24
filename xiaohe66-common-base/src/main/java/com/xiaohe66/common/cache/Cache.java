package com.xiaohe66.common.cache;

/**
 * @author xiaohe
 * @since 2022.01.21 11:16
 */
public interface Cache<K, V> {

    void put(K key, V value);

    V remove(K key);

    V get(K key);

    void clear();

}
