package com.xiaohe66.commons.test.util.rsa;

import com.xiaohe66.common.model.RsaKey;
import com.xiaohe66.common.util.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaohe
 * @time 2020.01.16 15:25
 */
@Slf4j
public class RsaUtilsTest {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Before
    public void before() {

        RsaKey key = RsaUtils.createKey();
        log.info("privateKey : {}", key.getPrivateKey());
        log.info("publicKey : {}", key.getPublicKey());

        publicKey = RsaUtils.getPublicKey(key.getPublicKey());
        privateKey = RsaUtils.getPrivateKey(key.getPrivateKey());

    }

    @Test
    public void testShort() {
        test("Hello world! 你好,世界。");
    }

    @Test
    public void testLong() {
        String sourceStr = "Hello world! 你好,世界。";
        int eachTimes = 10;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < eachTimes; i++) {
            stringBuilder.append(sourceStr);
        }
        String testText = stringBuilder.toString();
        log.info("test long, length : {}", testText.length());

        test(testText);
    }

    private void test(String originString) {

        String cipherText = RsaUtils.encryptToBase64(originString, publicKey);

        log.info("原文 : {}", originString);
        log.info("密文长度 : {}, 密文 : {}", cipherText.length(), cipherText);

        String text = RsaUtils.decryptToString(cipherText, privateKey);
        log.info("明文 : {}", text);

        assertEquals(originString, text);
    }
}
