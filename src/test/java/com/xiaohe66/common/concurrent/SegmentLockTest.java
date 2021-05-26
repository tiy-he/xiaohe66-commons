package com.xiaohe66.common.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

public class SegmentLockTest {

    SegmentLock<Integer> myLock = new SegmentLock<>();

    int[] arr = new int[myLock.getSize()];

    int threadQty = 2;

    int addQty = 50;

    CountDownLatch countDownLatch;

    /* 分段锁 */
    @Test
    public void test1() throws InterruptedException {
        countDownLatch = new CountDownLatch(threadQty * myLock.getSize());
        long time = System.currentTimeMillis();
        for (int i = 0; i < threadQty; i++) {
            for (int j = 0; j < myLock.getSize(); j++) {
                startThread(j, (index) -> myLock.getLock(index));
            }
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(Arrays.toString(arr));
    }

    /* 单锁 */
    @Test
    public void test2() throws InterruptedException {
        countDownLatch = new CountDownLatch(threadQty * myLock.getSize());

        long time = System.currentTimeMillis();
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < threadQty; i++) {
            for (int j = 0; j < myLock.getSize(); j++) {
                startThread(j, (index) -> lock);
            }
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(Arrays.toString(arr));
    }


    private void startThread(int j, Function<Integer, ReentrantLock> function) {
        new Thread(() -> {
            try {
                for (int i = 0; i < addQty; i++) {
                    ReentrantLock lock = function.apply(j);
                    lock.lock();
                    try {
                        arr[j]++;
                        // 生产环境上，正常运行都是有一些延迟的
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                }
            } finally {
                countDownLatch.countDown();
            }
        }).start();
    }

}