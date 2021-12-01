package com.xiaohe66.common.util;

import com.xiaohe66.common.util.time.DateTimeFormatters;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void test0() {

        long id = IdWorker.genId();
        long currentTimeMillis = System.currentTimeMillis();

        long timestamp = IdWorker.takeTimestamp(id);

        System.out.println(timestamp);
        System.out.println(currentTimeMillis);

        assertTrue(currentTimeMillis - timestamp < 1000);
    }

    @Test
    public void test00() {
        assertEquals(IdWorker.START_TIME, IdWorker.takeTimestamp(1));
        LocalDateTime localDateTime = IdWorker.takeLocalDateTime(1);

        LocalDateTime correct = LocalDateTime.of(2020, 03, 17, 20, 13, 14);
        assertEquals(correct, localDateTime);

        assertEquals("2020-03-17 20:13:14",IdWorker.takeDate(1));
    }

    @Test
    public void test1() {

        int n = Integer.MAX_VALUE >> 6;
        long[] arr = new long[n];

        arr[0] = IdWorker.genId();

        for (int i = 1; i < n; i++) {
            long id = IdWorker.genId();
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

        idSet.put(IdWorker.genId(), empty);

        AtomicInteger times = new AtomicInteger();

        List<Long> existId = new CopyOnWriteArrayList<>();

        Runnable runnable = () -> {

            int i = index.incrementAndGet();

            try {

                while (i < n) {

                    if (!existId.isEmpty()) {
                        return;
                    }

                    long id = IdWorker.genId();
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