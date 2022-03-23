package com.xiaohe66.common.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohe
 * @since 2022.03.23 10:48
 */
public class XhExecutors {

    private XhExecutors() {

    }

    public static ExecutorService newFixedThreadPool(int nThreads, int queueLength, String prefix) {

        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueLength);
        PrefixThreadFactory threadFactory = new PrefixThreadFactory(prefix);

        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                queue, threadFactory);
    }

}
