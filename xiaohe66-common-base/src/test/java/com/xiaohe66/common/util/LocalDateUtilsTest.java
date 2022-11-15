package com.xiaohe66.common.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class LocalDateUtilsTest {

    @Test
    public void test1() {

        LocalDate date = LocalDate.of(1970, 1, 2);
        assertEquals(60 * 60 * (24 - 8), LocalDateUtils.getTime(date));

        LocalDateTime dateTime = date.atTime(LocalTime.of(1, 0));
        assertEquals(60 * 60 * (24 - 8 + 1), LocalDateUtils.getTime(dateTime));

    }

}
