package com.xiaohe66.common.util.concurrent;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xiaohe
 * @since 2022.02.23 10:58
 */
@RequiredArgsConstructor
public class PrefixThreadFactory implements ThreadFactory {

    private final ThreadFactory threadFactory = Executors.defaultThreadFactory();
    private final AtomicLong num = new AtomicLong();

    private final String namePrefix;

    @Override
    public Thread newThread(@NonNull Runnable runnable) {

        Thread thread = threadFactory.newThread(runnable);
        thread.setName(namePrefix + num.incrementAndGet());

        return thread;
    }
}
