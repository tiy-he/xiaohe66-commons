package com.xiaohe66.common.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IdWorkerTest {

    @Test
    public void test1() {

        int n = Integer.MAX_VALUE >> 6;
        long[] arr = new long[n];

        arr[0] = IdWorker.getId();

        for (int i = 1; i < n; i++) {
            long id = IdWorker.getId();
            int index = Arrays.binarySearch(arr, 0, i, id);
            assertTrue(index == i || index < 0);
            assertTrue(arr[i - 1] < id);
            arr[i] = id;
        }

    }

    @Test
    public void test2() throws InterruptedException {

        int threads = 2;
        int n = 1 << 20;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        CountDownLatch countDownLatch = new CountDownLatch(threads);

        Map<Long, Object> idSet = new ConcurrentHashMap<>(n);

        Object empty = new Object();
        AtomicInteger index = new AtomicInteger();

        idSet.put(IdWorker.getId(), empty);

        AtomicInteger times = new AtomicInteger();

        List<Long> existId = new CopyOnWriteArrayList<>();

        Runnable runnable = () -> {

            int i = index.incrementAndGet();

            try {

                while (i < n) {

                    if (!existId.isEmpty()) {
                        return;
                    }

                    long id = IdWorker.getId();
                    if (idSet.containsKey(id)) {
                        existId.add(id);
                        return;
                    }
                    idSet.put(id, empty);
                    System.out.println(id);

                    i = index.incrementAndGet();
                }
                times.incrementAndGet();

            } catch (Throwable e) {
                e.printStackTrace();

            } finally {
                countDownLatch.countDown();
            }

        };

        for (int i = 0; i < threads; i++) {
            executorService.submit(runnable);
        }

        countDownLatch.await();
        System.out.println("重复的id : " + existId);
        assertEquals(threads, times.get());

    }

}