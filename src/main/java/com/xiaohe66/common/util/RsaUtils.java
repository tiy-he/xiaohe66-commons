package com.xiaohe66.common.util;

import com.xiaohe66.common.ex.XhRuntimeException;
import com.xiaohe66.common.model.RsaKey;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author xiaohe
 * @time 2020.01.16 11:33
 */
@Slf4j
public class RsaUtils {

    private static final String RSA = "RSA";

    private RsaUtils() {
    }

    /**
     * 创建公私钥对
     */
    public static RsaKey createKey() {
        return createKey(1024);
    }

    /**
     * 创建公私钥对
     */
    public static RsaKey createKey(int keyLength) {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            throw new XhRuntimeException(e);
        }
        keyPairGenerator.initialize(keyLength);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String privateKryStr = Base64Utils.encode(keyPair.getPrivate().getEncoded());
        String publicKeyStr = Base64Utils.encode(keyPair.getPublic().getEncoded());

        return new RsaKey(privateKryStr, publicKeyStr);
    }

    /**
     * 公钥加密
     */
    public static byte[] encrypt(byte[] content, PublicKey publicKey) {

        int segmentSize = 1024 / 8 - 11;

        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 对数据分段解密
            return cipherDoFinal(cipher, content, segmentSize);

        } catch (Exception e) {
            throw new XhRuntimeException(e);
        }
    }

    /**
     * 公钥加密
     */
    public static byte[] encrypt(String content, PublicKey publicKey) {
        return encrypt(content.getBytes(), publicKey);
    }

    public static String encryptToBase64(String content, PublicKey publicKey) {
        return Base64Utils.encode(encrypt(content, publicKey));
    }

    public static String encryptToBase64(byte[] content, PublicKey publicKey) {
        return Base64Utils.encode(encrypt(content, publicKey));
    }

    public static byte[] decrypt(byte[] content, PrivateKey privateKey) {

        int segmentSize = 1024 / 8;

        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 分段解密
            return cipherDoFinal(cipher, content, segmentSize);

        } catch (Exception e) {
            throw new XhRuntimeException(e);
        }
    }

    /**
     * 私钥解密
     */
    public static byte[] decrypt(String content, PrivateKey privateKey) {
        return decrypt(content.getBytes(), privateKey);
    }

    public static String decryptToString(byte[] content, PrivateKey privateKey) {
        return new String(decrypt(content, privateKey));
    }

    public static String decryptToString(String content, PrivateKey privateKey) {
        byte[] bytes = Base64Utils.decodeToByteArr(content);
        return new String(decrypt(bytes, privateKey));
    }


    private static byte[] cipherDoFinal(Cipher cipher, byte[] content, int segmentSize) {
        int inputLen = content.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > segmentSize) {
                    cache = cipher.doFinal(content, offSet, segmentSize);
                } else {
                    cache = cipher.doFinal(content, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * segmentSize;
            }
            return out.toByteArray();

        } catch (Exception e) {
            throw new XhRuntimeException(e);
        }
    }

    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     */
    public static PublicKey getPublicKey(String publicKey) {
        byte[] keyBytes = Base64Utils.decodeToByteArr(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new XhRuntimeException(e);
        }
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        byte[] keyBytes = Base64Utils.decodeToByteArr(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new XhRuntimeException(e);
        }
    }

}
