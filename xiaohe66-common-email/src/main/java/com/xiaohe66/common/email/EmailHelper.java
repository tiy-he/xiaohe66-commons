package com.xiaohe66.common.email;

import com.xiaohe66.common.email.bo.EmailAttachment;
import com.xiaohe66.common.email.bo.EmailImage;
import com.xiaohe66.common.email.ex.EmailException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author xiaohe
 * @time 2020.03.06 10:49
 */
public class EmailHelper {

    private static final Logger log = LoggerFactory.getLogger(EmailHelper.class);

    private EmailHelper() {
    }

    /**
     * 将一个用逗号隔开的邮件地址字符串，切分成 {@link InternetAddress} 对象数组表示
     *
     * @param addressStr 用逗号隔开的邮箱地址字符串。如: e1@qq.com,e2@qq.com
     * @return InternetAddress[]
     */
    public static InternetAddress[] splitAddress(String addressStr) {
        return splitAddress(addressStr, ",");
    }

    /**
     * 将一个用某个符号隔开的邮件地址字符串，切分成 {@link InternetAddress} 对象数组表示
     *
     * @param addressStr 用逗号隔开的邮箱地址字符串。如: e1@qq.com,e2@qq.com
     * @param regex      分隔符
     * @return InternetAddress[]
     */
    public static InternetAddress[] splitAddress(String addressStr, String regex) {
        if (StringUtils.isEmpty(regex)) {
            throw new IllegalArgumentException("regex cannot be empty");
        }
        if (StringUtils.isEmpty(addressStr)) {
            return new InternetAddress[0];
        }
        String[] address = addressStr.split(regex);
        InternetAddress[] addressArr = new InternetAddress[address.length];
        for (int i = 0; i < address.length; i++) {
            try {
                addressArr[i] = new InternetAddress(address[i], address[i], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("创建地址对象发生错误, message : {}", e.getMessage());
            }
        }
        return addressArr;
    }

    public static BodyPart buildImageBodyPart(EmailImage image) throws MessagingException {
        BodyPart imagePart = buildBodyPart(image.getImageFile());
        imagePart.setHeader("Content-Location", image.getUrl());
        return imagePart;
    }

    public static BodyPart buildAttachmentBodyPart(EmailAttachment attachment) throws MessagingException {
        BodyPart attchmentPart = buildBodyPart(attachment.getFile());
        try {
            attchmentPart.setFileName(MimeUtility.encodeText(attachment.getFileName()));
        } catch (UnsupportedEncodingException e) {
            throw new EmailException(e);
        }
        return attchmentPart;
    }

    private static BodyPart buildBodyPart(File file) throws MessagingException {
        FileDataSource fileDataSource = new FileDataSource(file);
        DataHandler dataHandler = new DataHandler(fileDataSource);
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setDataHandler(dataHandler);
        return bodyPart;
    }

    public static String joinAddressString(InternetAddress[] addresses) {
        if (ArrayUtils.isEmpty(addresses)) {
            return "";
        }
        StringBuilder addressStr = new StringBuilder();
        for (InternetAddress address : addresses) {
            addressStr.append(",").append(address.getAddress());
        }
        return addressStr.length() > 0 ? addressStr.substring(1) : "";
    }

}
