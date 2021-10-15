package com.xiaohe66.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xiaohe
 * @time 2020.07.03 16:15
 * @see com.google.common.base.Joiner
 */
public class JoinUtils {

    private JoinUtils() {

    }

    public static String join(Collection<?> param) {
        return join(param, ",");
    }

    public static String join(Collection<?> param, String separator) {

        Objects.requireNonNull(param);
        Objects.requireNonNull(separator);

        Iterator<?> iterator = param.iterator();

        if (iterator.hasNext()) {

            StringBuilder joinStr = new StringBuilder();
            joinStr.append(iterator.next());

            while (iterator.hasNext()) {
                joinStr.append(separator).append(iterator.next());
            }

            return joinStr.toString();

        }

        return "";
    }

    public static String join(Object[] param) {
        return join(param, ",");
    }

    public static String join(Object[] param, String separator) {

        Objects.requireNonNull(param);
        Objects.requireNonNull(separator);

        if (param.length == 1) {
            return toString(param[0]);
        }

        StringBuilder result = new StringBuilder(toString(param[0]));

        for (int i = 1; i < param.length; i++) {
            result.append(separator).append(toString(param[i]));
        }

        return result.toString();
    }

    public static String join(int[] param) {
        return join(param, ",");
    }

    public static String join(int[] param, String separator) {

        Object[] arr = new Object[param.length];
        for (int i = 0; i < param.length; i++) {
            arr[i] = param[i];
        }
        return join(arr, separator);
    }

    public static String join(long[] param) {
        return join(param, ",");
    }

    public static String join(long[] param, String separator) {

        Object[] arr = new Object[param.length];
        for (int i = 0; i < param.length; i++) {
            arr[i] = param[i];
        }
        return join(arr, separator);
    }

    public static String join(char[] param) {
        return join(param, ",");
    }

    public static String join(char[] param, String separator) {

        Object[] arr = new Object[param.length];
        for (int i = 0; i < param.length; i++) {
            arr[i] = param[i];
        }
        return join(arr, separator);
    }

    private static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String join(List<Object> list){
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
