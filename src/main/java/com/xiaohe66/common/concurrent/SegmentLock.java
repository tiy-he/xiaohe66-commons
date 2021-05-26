package com.xiaohe66.common.concurrent;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分段锁
 *
 * @author xiaohe
 * @time 2021.05.25 17:21
 */
public class SegmentLock<E> {

    private static final int DEFAULT_SIZE = 1 << 4;

    private static final int MAX_SIZE = 1024;

    private final ReentrantLock[] lock;

    private final int size;

    public SegmentLock() {
        this(DEFAULT_SIZE);
    }

    public SegmentLock(int size) {
        size = getSizeFor(size);
        this.size = size;
        lock = new ReentrantLock[size];
        for (int i = 0; i < size; i++) {
            lock[i] = new ReentrantLock();
        }
    }

    public ReentrantLock getLock(E key) {
        Objects.requireNonNull(key);

        return lock[key.hashCode() & (size - 1)];
    }

    protected int getSizeFor(int size) {
        int n = size - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        if (n < 0) {
            return 1;
        }
        return n >= MAX_SIZE ? MAX_SIZE : n + 1;
    }

    public int getSize() {
        return size;
    }
}
