package com.xiaohe66.common.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 雪花ID生成器（线程安全）
 * <p>
 * 规则 ：【时间41位】【机器号10位】【序列号12位】
 *
 * @author xiaohe
 * @time 2021.08.11 10:01
 */
public class IdWorker {

    private static final int WORKER_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;
    private static final int WORKER_ID_OFFSET = SEQUENCE_BITS;
    private static final int TIMESTAMP_OFFSET = SEQUENCE_BITS + WORKER_ID_BITS;

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 序列号最大值
     */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    private final AtomicBoolean lock = new AtomicBoolean(true);

    private final AtomicLong sequence = new AtomicLong();

    private final long workerId;

    private long lastTime = System.currentTimeMillis();

    public IdWorker(long workerId) {
        if (workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(String.format("workerId is too big, max : %s, current : %s", MAX_WORKER_ID, workerId));
        }
        this.workerId = workerId;
    }

    public long next() {
        for (; ; ) {
            if (lock.compareAndSet(true, false)) {
                try {
                    long currentTime = System.currentTimeMillis();

                    if (currentTime < lastTime) {
                        throw new IllegalStateException(String.format("Clock moved backwards. last : %s ,current : %s", lastTime, currentTime));
                    }

                    if (currentTime != lastTime) {
                        lastTime = currentTime;
                        sequence.set(0);
                    }

                    long num = sequence.incrementAndGet();
                    if (num > MAX_SEQUENCE) {
                        Thread.sleep(5);
                        continue;
                    }

                    return doNext(currentTime, num);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                } finally {
                    lock.set(true);
                }
            }
        }
    }

    private long doNext(long time, long sequence) {

        return (time << TIMESTAMP_OFFSET) |
                (workerId << WORKER_ID_OFFSET) |
                sequence;
    }
}
