package com.xiaohe66.common.cache.impl;

import com.xiaohe66.common.cache.Cache;
import com.xiaohe66.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
public class SimpleCacheTest {

    private final Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterRead(2, TimeUnit.SECONDS)
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    @Test
    public void test1() throws InterruptedException {

        cache.put("one", "1");
        cache.put("three", "3");

        Thread.sleep(1000);

        assertEquals("1", cache.get("one"));

        cache.put("two", "2");

        Thread.sleep(2500);

        assertNull(cache.get("one"));
        assertNull(cache.get("two"));
        assertNull(cache.get("three"));
    }

    @Test
    public void test2() {

        final Cache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterRead(6, TimeUnit.SECONDS)
                .build();

        cache.put("one","1");

    }
}