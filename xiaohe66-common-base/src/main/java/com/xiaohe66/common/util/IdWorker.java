package com.xiaohe66.common.util;

/**
 * 雪花ID生成器（线程安全）
 * <p>
 * 规则 ：[0][时间戳:41位][数据中心:5位][工作id:5位][序列号:12位]
 *
 * @author xiaohe
 * @time 2021.08.11 10:01
 */
public final class IdWorker {

    private static final long START_TIME = 1584447194666L;

    private static Sequence sequence = new Sequence(START_TIME);

    private IdWorker() {
    }

    public static long genId() {
        return sequence.nextId();
    }

    public static String genIdStr() {
        return String.valueOf(sequence.nextId());
    }
}
