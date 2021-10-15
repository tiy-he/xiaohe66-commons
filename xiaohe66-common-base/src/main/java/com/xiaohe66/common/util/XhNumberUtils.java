package com.xiaohe66.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xiaohe
 * @time 2020.05.11 10:08
 */
public class XhNumberUtils {

    private XhNumberUtils(){}

    /**
     * 尝试修复丢失的精度
     * <p>
     * 900029.7000000001 = 900029.7
     * 900034.2000000001 = 900034.2
     * 900038.7000000001 = 900038.7
     */
    public static double tryRepairScale(String value) throws NumberFormatException {

        /*
         * 修复思路:
         * 统计小数点后面的重复数字数量,当重复某个数字达到阀值时,做四舍五入操作
         */

        int length = value.length();

        // 总位数小于15位时,不是精度丢失的数(精度丢失的数会有很大的小数部分)
        if (length < 15) {
            return Double.parseDouble(value);
        }

        // 精度丢失必然会出现很多0或9
        char prevChar = value.charAt(length - 3);
        if (prevChar != '0' && prevChar != '9') {
            return Double.parseDouble(value);
        }

        int sameTimes = 0;
        int dotIndex = value.indexOf('.');
        for (int i = length - 4; i > dotIndex; i--) {
            if (prevChar == value.charAt(i)) {
                sameTimes++;
            } else {
                break;
            }
        }

        // 由于整数部分位数较多时,小数位数就比较小
        // 这里减去一个整数部分的位数,可以最大可能的正确判断这个数为精度丢失的数
        if (sameTimes >= 5 && sameTimes > 10 - dotIndex) {

            // 四舍五入的精度由小数点到重复数字的首次出现的位置得出
            int scale = length - 3 - sameTimes - dotIndex;

            BigDecimal valueBig = new BigDecimal(value);

            return valueBig.setScale(scale, RoundingMode.HALF_UP).doubleValue();
        } else {

            // 相同数字出现的次数不足以断定该数为精度丢失的数,直接返回该数的double形式
            return Double.parseDouble(value);
        }
    }

    public static Object tryToLong(double value) {
        long cellValueLong = (long) value;

        if ((double) cellValueLong == value) {
            return cellValueLong;

        } else {
            return value;
        }
    }

}
