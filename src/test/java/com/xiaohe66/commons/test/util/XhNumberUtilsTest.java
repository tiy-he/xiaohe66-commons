package com.xiaohe66.commons.test.util;

import com.xiaohe66.common.util.XhNumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaohe
 * @time 2020.05.11 10:31
 */
@Slf4j
public class XhNumberUtilsTest {

    private Random random = new Random(System.currentTimeMillis());

    @Test
    public void main() {

        long start = 1;

        Random random = new Random(System.currentTimeMillis());

        while (start < 10000000) {

            double divisor = randomDivisor();
            log.info("---------------divisor : {}---------------", divisor);

            for (int i = 0; i <= 100; i++) {

                test(start + i, divisor);
            }
            start = start * 10;
            log.info("------------------------------------------");
        }
    }

    private void test(long value, double divisor) {
        double correctValue = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(divisor)).doubleValue();
        double originValue = value * divisor;
        double resultValue = XhNumberUtils.tryRepairScale(String.valueOf(originValue));

        log.info("correct : {}, result : {}, origin : {}", correctValue, resultValue, originValue);
        assertEquals(correctValue, resultValue, 0);
    }

    private double randomDivisor(){
        double divisor;
        do {
            divisor = BigDecimal.valueOf(random.nextInt(100)).multiply(BigDecimal.valueOf(0.1)).doubleValue();
        }while (divisor == 0);
        return divisor;
    }

}
