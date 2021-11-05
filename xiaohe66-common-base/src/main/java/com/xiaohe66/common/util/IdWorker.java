package com.xiaohe66.common.util;

/**
 * 雪花ID生成器（线程安全）
 * <p>
 * 规则 ：【时间41位】【机器号10位】【序列号12位】
 *
 * @author xiaohe
 * @time 2021.08.11 10:01
 */
public final class IdWorker {

    private static Sequence sequence = new Sequence();

    private IdWorker() {
    }

    public static long getId() {
        return sequence.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(sequence.nextId());
    }
}
