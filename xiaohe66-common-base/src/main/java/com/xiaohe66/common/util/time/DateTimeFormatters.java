package com.xiaohe66.common.util.time;

import java.time.format.DateTimeFormatter;

/**
 * @author xiaohe
 * @since 2021.12.01 16:00
 */
public class DateTimeFormatters {

    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_DOT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final DateTimeFormatter DATE_UNSIGNED = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter TIME_UNSIGNED = DateTimeFormatter.ofPattern("HHmmss");

    public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_DOT = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    public static final DateTimeFormatter DATE_TIME_UNSIGNED = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter YEAR_MONTH = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter YEAR_MONTH_DOT = DateTimeFormatter.ofPattern("yyyy.MM");
    public static final DateTimeFormatter YEAR_MONTH_UNSIGNED = DateTimeFormatter.ofPattern("yyyyMM");

    public static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("MM-dd");
    public static final DateTimeFormatter MONTH_DAY_UNSIGNED = DateTimeFormatter.ofPattern("MMdd");

    protected DateTimeFormatters() {

    }

}
