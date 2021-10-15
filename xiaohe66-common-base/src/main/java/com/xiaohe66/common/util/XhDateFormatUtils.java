package com.xiaohe66.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期相关工具类
 * <p>正常是带格式的，日期用 “-”分开，日间用“：”分开
 * <p>所有格式后面带个2的，这些是不带格式的
 *
 * @author xiaohe
 * @time 2019-10-29 18:37
 */
public class XhDateFormatUtils {

    private XhDateFormatUtils() {
    }

    public static final DateFormat yyyyMMddHHmmss;
    public static final DateFormat yyyyMMddHHmmss2;
    public static final DateFormat yyyyMMdd;
    public static final DateFormat yyyyMMdd2;
    public static final DateFormat yyyyMM;
    public static final DateFormat yyyyMM2;
    public static final DateFormat MMdd;
    public static final DateFormat MMdd2;
    public static final DateFormat HHmmss;
    public static final DateFormat HHmmss2;

    static {
        yyyyMMddHHmmss = new DateFormat(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss"));
        yyyyMMddHHmmss2 = new DateFormat(FastDateFormat.getInstance("yyyyMMddHHmmss"));
        yyyyMMdd = new DateFormat(FastDateFormat.getInstance("yyyy-MM-dd"));
        yyyyMMdd2 = new DateFormat(FastDateFormat.getInstance("yyyyMMdd"));
        yyyyMM = new DateFormat(FastDateFormat.getInstance("yyyy-MM"));
        yyyyMM2 = new DateFormat(FastDateFormat.getInstance("yyyyMM"));
        MMdd = new DateFormat(FastDateFormat.getInstance("MM-dd"));
        MMdd2 = new DateFormat(FastDateFormat.getInstance("MMdd"));
        HHmmss = new DateFormat(FastDateFormat.getInstance("HH:mm:ss"));
        HHmmss2 = new DateFormat(FastDateFormat.getInstance("HHmmss"));
    }

    public static class DateFormat {

        private final FastDateFormat fastDateFormat;

        private DateFormat(FastDateFormat dateFormat) {
            this.fastDateFormat = dateFormat;
        }

        public String format(Date date) {
            return fastDateFormat.format(date);
        }

        public Date parse(String date) throws ParseException {
            return fastDateFormat.parse(date);
        }
    }
}
