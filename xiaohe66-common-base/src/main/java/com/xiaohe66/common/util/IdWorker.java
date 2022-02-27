package com.xiaohe66.common.util;

import com.xiaohe66.common.util.time.DateTimeFormatters;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 雪花ID生成器（线程安全）
 * <p>
 * 规则 ：[0][时间戳:41位][数据中心:5位][工作id:5位][序列号:12位]
 *
 * @author xiaohe
 * @time 2021.08.11 10:01
 */
public final class IdWorker {

    public static final long START_TIME = 1584447194666L;

    private static final Sequence sequence = new Sequence(START_TIME);

    private IdWorker() {
    }

    public static long genId() {
        return sequence.nextId();
    }

    public static String genIdStr() {
        return String.valueOf(sequence.nextId());
    }

    /**
     * 根据指定时间生成一个id
     */
    public static long buildId(long timestamp) {
        return ((timestamp - START_TIME) << Sequence.TIMESTAMP_LEFT_SHIFT);
    }

    public static long takeTimestamp(long id) {
        return (id >> Sequence.TIMESTAMP_LEFT_SHIFT) + START_TIME;
    }

    public static LocalDateTime takeLocalDateTime(long id) {
        long timestamp = takeTimestamp(id) / 1000;
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    public static String takeDate(long id) {
        LocalDateTime localDateTime = takeLocalDateTime(id);
        return localDateTime.format(DateTimeFormatters.DATE_TIME);
    }
}
