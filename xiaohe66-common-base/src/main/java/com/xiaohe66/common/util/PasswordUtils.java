package com.xiaohe66.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xiaohe
 * @since 2021.11.02 16:29
 */
public final class PasswordUtils {

    public static final int PASSWORD_LEN = 64;
    public static final int DEFAULT_SALT_LEN = 24;
    public static final int DEFAULT_HASH_LEN = PASSWORD_LEN - DEFAULT_SALT_LEN;

    private static final char[] HEX_CHAR_ARR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private PasswordUtils() {

    }

    public static String genPwd(String text) {

        Objects.requireNonNull(text);

        String salt = genSalt();

        String hash = DigestUtils.sha1Hex(text + salt);

        return salt + hash;
    }

    public static boolean verify(String text, String password) {

        Objects.requireNonNull(text);
        Objects.requireNonNull(password);

        String salt = getSalt(password);
        String hash = getHash(password);

        String pwd = DigestUtils.sha1Hex(text + salt);

        return pwd.equals(hash);
    }

    public static String genSalt() {
        return genSalt(DEFAULT_SALT_LEN);
    }

    public static String genSalt(int len) {
        if (len <= 0 || len > 32) {
            throw new IllegalArgumentException("len must be between 1 and 32");
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        char[] salt = new char[len];
        for (int i = 0; i < len; i++) {
            int num = random.nextInt(16);

            salt[i] = HEX_CHAR_ARR[num];
        }

        return new String(salt);
    }

    public static String getSalt(String text) {
        return text.substring(0, DEFAULT_SALT_LEN);
    }

    public static String getHash(String text) {
        return text.substring(DEFAULT_SALT_LEN);
    }

}
