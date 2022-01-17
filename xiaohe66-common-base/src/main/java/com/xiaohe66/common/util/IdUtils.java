package com.xiaohe66.common.util;

import java.util.UUID;

/**
 * @author xiaohe
 * @since 2022.01.17 10:39
 */
public class IdUtils {

    /*
    TODO ： 也许应该用国人的工具包 hutool
     */

    protected IdUtils() {

    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuidSimple() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
