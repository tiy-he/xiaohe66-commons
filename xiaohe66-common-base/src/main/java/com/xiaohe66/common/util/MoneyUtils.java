package com.xiaohe66.common.util;

import java.math.BigDecimal;

/**
 * @author xiaohe
 * @time 2021.07.08 18:08
 */
public class MoneyUtils {

    private MoneyUtils() {

    }

    public static String fenToYuanStr(long fen) {
        return fenToYuanBig(BigDecimal.valueOf(fen)).toString();
    }

    public static String fenToYuanStr(String fen) {
        return fenToYuanBig(fen).toString();
    }

    public static BigDecimal fenToYuanBig(String fen) {
        return new BigDecimal(fen).movePointLeft(2);
    }

    public static BigDecimal fenToYuanBig(BigDecimal fen) {
        return fen.movePointLeft(2);
    }

    public static long yuanToFen(String yuan) {
        return yuanToFenBig(yuan).longValue();
    }

    public static BigDecimal yuanToFenBig(String yuan) {
        return new BigDecimal(yuan).movePointRight(2);
    }
}
