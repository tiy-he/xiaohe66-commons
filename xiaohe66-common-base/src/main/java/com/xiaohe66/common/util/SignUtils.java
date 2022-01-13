package com.xiaohe66.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * xiaohe 的签名工具类
 *
 * @author xiaohe
 * @since 2022.01.05 15:13
 */
@Slf4j
public final class SignUtils {

    private SignUtils() {

    }

    /**
     * 创建待签名的源字符串
     *
     * @param method    请求类型(GET/POST/PUT/DELETE)
     * @param url       请求的链接（不带域名，带?后面的参数）例：/test/api?a=1
     * @param timestamp 当前时间戳（豪秒）
     * @param body      有body的时候传入，若没有，则不传入
     * @return 待签名的源字符串
     */
    public static String getTmpString(String method, String url, long timestamp, String body, String secret) {

        return method + "YZ" +
                url + "YZ" +
                timestamp + "YZ" +
                body + "YZ" +
                secret;
    }

    public static String signMd5(String method, String url, long timestamp, String body, String secret) {

        String tmpString = getTmpString(method, url, timestamp, body, secret);

        log.debug("sign tmpString : {}", tmpString);

        String correctSign = DigestUtils.md5Hex(tmpString);

        log.debug("sign result: {}", correctSign);

        return correctSign;
    }

    public static boolean verifyMd5(String method, String url, long timestamp, String body, String secret, String sign) {
        String correctSign = signMd5(method, url, timestamp, body, secret);
        return correctSign.equals(sign);
    }

}
