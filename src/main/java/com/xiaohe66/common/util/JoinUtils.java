package com.xiaohe66.common.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author xiaohe
 * @time 2020.07.03 16:15
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

}
