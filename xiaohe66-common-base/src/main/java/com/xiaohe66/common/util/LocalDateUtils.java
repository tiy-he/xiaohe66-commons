package com.xiaohe66.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohe
 * @since 2020.07.15 12:01
 */
public class LocalDateUtils {

    private LocalDateUtils() {

    }

    public static List<LocalDate> getDateList(LocalDate begin, LocalDate end) {

        List<LocalDate> list = new ArrayList<>();

        for (LocalDate tmp = begin; tmp.isBefore(end); tmp = tmp.plusDays(1)) {
            list.add(tmp);
        }
        list.add(end);

        return list;
    }

    /**
     * 计算相差几天，例：2022.01.01-2022.01.02 为相差1天
     */
    public static int getDays(Temporal begin, Temporal end) {
        return (int) ChronoUnit.DAYS.between(begin, end);
    }

    public static LocalDate getMonday(LocalDate date) {
        DayOfWeek week = date.getDayOfWeek();
        return date.minusDays((long) week.getValue() - 1);
    }

    public static long getTime(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public static long getTime(LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

}
