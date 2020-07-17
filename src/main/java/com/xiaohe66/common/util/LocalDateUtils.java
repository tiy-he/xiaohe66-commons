package com.xiaohe66.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohe
 * @time 2020.07.15 12:01
 */
public class LocalDateUtils {

    private LocalDateUtils(){

    }

    public static List<LocalDate> getDateList(LocalDate begin, LocalDate end) {

        List<LocalDate> list = new ArrayList<>();

        for (LocalDate tmp = begin; tmp.isBefore(end); tmp = tmp.plusDays(1)) {
            list.add(tmp);
        }
        list.add(end);

        return list;
    }

    public static int getDays(LocalDate begin, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(begin, end) + 1;
    }

    public static LocalDate getMonday(LocalDate date) {
        DayOfWeek week = date.getDayOfWeek();
        return date.minusDays((long)week.getValue() - 1);
    }

}
