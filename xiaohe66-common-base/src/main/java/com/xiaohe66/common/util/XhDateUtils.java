package com.xiaohe66.common.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author xiaohe
 * @time 2019.08.27 10:07
 */
public class XhDateUtils {

    private XhDateUtils() {
    }

    public static Date firstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    public static Date lastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    /**
     * 生成指定日期区间内的所有日期
     *
     * @param beginDate 开始日期（包含）
     * @param endDate   结束日期（包含）
     * @author xiaohe 2019.09.18
     */
    public static List<Date> createDateInRange(Date beginDate, Date endDate) {
        List<Date> result = new ArrayList<>();

        Date date;
        for (date = beginDate; date.before(endDate); date = DateUtils.addDays(date, 1)) {
            result.add(date);
        }
        result.add(endDate);
        return result;
    }

    /**
     * 遍历指定日期的所在周的周一到指定日期当天的所有天数
     */
    public static List<Date> createDateMonToCurrent(Date endDate) {
        // Calendar返回的星期是从周日开始算起的，周日为1，周一为2
        Date date = getCurrentWeekMonday(endDate);

        List<Date> list = new ArrayList<>();

        for (; date.before(endDate); date = DateUtils.addDays(date, 1)) {
            list.add(date);
        }
        list.add(date);
        return list;
    }

    public static Date getCurrentWeekMonday(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int week = calendar.get(Calendar.DAY_OF_WEEK);

        // Calendar返回的星期是从周日开始算起的，周日为1，周一为2
        return week == Calendar.SUNDAY ?
                DateUtils.addDays(date, -6) :
                DateUtils.addDays(date, 2 - week);
    }

    /**
     * 返回上一个周三的日期，若传入的是周三，则返回传入的日期
     */
    public static Date getLastWednesday(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int week = calendar.get(Calendar.DAY_OF_WEEK);

        // Calendar返回的星期是从周日开始算起的，周日为1，周一为2
        switch (week) {
            case Calendar.SUNDAY:
            case Calendar.MONDAY:
            case Calendar.TUESDAY:
                return DateUtils.addDays(date, -week - 3);
            case Calendar.WEDNESDAY:
                return date;
            case Calendar.THURSDAY:
            case Calendar.FRIDAY:
            case Calendar.SATURDAY:
                return DateUtils.addDays(date, -week + 4);
            default:
                throw new IllegalStateException();
        }
    }

}
