package com.xiaohe66.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohe
 * @since 2022.03.07 10:54
 */
@Slf4j
public class XhJwt {

    @Getter
    private final int expirationMinutes;

    @Getter
    private final String issuer;

    @Getter
    private final Set<String> blacklist = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private MyVerifier verifier;
    private MyVerifier verifierOld;

    /**
     * @param expirationMinutes token有效期，建议 7天以上
     * @param issuer            签发者。在分布式环境中，建议设置机器号
     * @param secret            密钥
     */
    public XhJwt(int expirationMinutes, @NonNull String issuer, @NonNull String secret) {
        this.expirationMinutes = expirationMinutes;
        this.issuer = issuer;
        this.verifier = new MyVerifier(secret);
    }

    public String genToken(CurrentAccount account) {
        return genToken(account, genExpirationDate());
    }

    public String genToken(CurrentAccount account, Date expiresAt) {

        final MyVerifier verifier = this.verifier;

        String accountJson = JsonUtils.toString(account);

        String encodeAccountJson = XhEncryptUtils.encode(accountJson);

        String token = JWT.create()
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .withClaim("info", encodeAccountJson)
                .sign(Algorithm.HMAC512(verifier.secret));

        log.debug("gen jwt : {}", token);

        return token;
    }

    public CurrentAccount verifyAndGet(String token) {

        DecodedJWT decoded = verify(token);

        String encodeAccountJson = decoded.getClaim("info").asString();
        String json = XhEncryptUtils.decode(encodeAccountJson);

        log.debug("json : {}", json);

        try {
            return JsonUtils.formatObject(json, CurrentAccount.class);

        } catch (JsonProcessingException e) {

            log.error("parse account info error, json : {}, token : {}", json, token);
            throw new BusinessException(ErrorCodeEnum.INVALID_TOKEN);
        }
    }

    public DecodedJWT verify(String token) {

        DecodedJWT decode = JWT.decode(token);

        if (blacklist.contains(decode.getIssuer())) {
            log.warn("blacklist, issuer : {}, token : {}", decode.getIssuer(), token);
            throw new BusinessException(ErrorCodeEnum.INVALID_TOKEN);
        }

        final MyVerifier verifier = this.verifier;

        try {
            return verifier.verify(decode);

        } catch (JWTVerificationException e) {

            final MyVerifier verifierOld = this.verifierOld;

            try {
                DecodedJWT ret = this.verifierOld.verify(decode);

                log.warn("token invalid, try secretOld success, token : {}", token);

                return ret;

            } catch (JWTVerificationException ignore) {

                log.error("token invalid, try secretOld error, secretOld : {}, token : {}", verifierOld.secret, token);
            }

            throw new BusinessException(ErrorCodeEnum.INVALID_TOKEN);
        }
    }

    private Date genExpirationDate() {
        return DateUtils.addMinutes(new Date(), expirationMinutes);
    }

    /**
     * 设置新的密钥, 旧的密钥将在指定时间内可用
     *
     * @param secret           新的密码
     * @param expiresTimestamp 旧密钥的有效截止时间（毫秒）
     */
    public void setSecret(@NonNull String secret, long expiresTimestamp) {

        // 这几句代码的顺序不能换
        MyVerifier verifier = new MyVerifier(secret);

        this.verifierOld = this.verifier;
        this.verifier = verifier;

        this.verifierOld.expiresTimestamp = expiresTimestamp;
    }

    public String getSecret() {
        return verifier.secret;
    }

    public String getSecretOld() {
        return verifierOld != null ? verifierOld.secret : null;
    }

    /**
     * 返回旧密钥的有效时间，若没有旧密钥，则返回 null
     *
     * @return 返回旧密钥的有效时间，若没有旧密钥，则返回 null
     */
    public Long getSecretOldExpiresTimestamp() {
        return verifierOld != null ? verifierOld.expiresTimestamp : null;
    }

    @Data
    public static class CurrentAccount {

        private Long id;
        private String name;
    }

    protected static class MyVerifier {

        protected final String secret;
        protected final JWTVerifier verifier;
        /**
         * 该密钥的有效截止时间，超过这个时间后，该密钥无效。当该值为null时，表示长期有效。
         */
        protected long expiresTimestamp;

        public MyVerifier(String secret) {
            this(secret, JWT.require(Algorithm.HMAC512(secret)).build());
        }

        public MyVerifier(String secret, JWTVerifier verifier) {
            this.secret = secret;
            this.verifier = verifier;
        }

        protected DecodedJWT verify(DecodedJWT decode) {

            if (expiresTimestamp <= 0) {
                return verifier.verify(decode);

            } else if (expiresTimestamp >= SystemClock.currentTimeMillis()) {
                return verifier.verify(decode);

            } else {
                throw new JWTVerificationException("token invalid");
            }
        }
    }

}
