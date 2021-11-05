package com.xiaohe66.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class PasswordUtilsTest {


    @Test
    public void test1() {

        String text = "1";

        String hash = PasswordUtils.genPwd(text);

        log.info(hash);

        assertEquals(64, hash.length());

        assertTrue(PasswordUtils.verify(text, hash));
    }

    @Test
    public void test2() {

        String text = "1";
        String password = "f16f7a5a0d043d8a57c854c667dce52258633ee0503c8f2ef9c8925b65e73312";

        assertTrue(PasswordUtils.verify(text, password));

    }
}