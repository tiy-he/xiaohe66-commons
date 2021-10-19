package com.xiaohe66.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author xiaohe
 * @since 2021.10.19 10:38
 */
@Slf4j
public class Slf4jTest {

    @Test
    public void test1() {
        log.info("info log");
    }

    @Test
    public void test2() {
        log.info("debug log");
    }

    @Test
    public void test3() {

        log.atInfo()
                .addArgument("p1")
                .addArgument("p2")
                .log(" {} hello {}");
    }

    @Test
    public void test4() {

        int newT = 15;
        int oldT = 16;

        // using classical API
        log.debug("oldT={} newT={} Temperature changed.", newT, oldT);

        // using fluent API
        log.atDebug().addKeyValue("oldT", oldT).addKeyValue("newT", newT).log("{oldT}{}Temperature changed.");

    }
}
