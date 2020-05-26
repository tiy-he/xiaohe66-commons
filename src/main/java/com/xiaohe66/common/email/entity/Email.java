package com.xiaohe66.common.email.entity;

import lombok.Getter;
import lombok.Setter;

import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.List;

/**
 * 表示一封邮件
 * <p>
 * 包含收件人、抄送人、主题、内容、附件等字段
 *
 * @author xiaohe
 * @time 2020.03.05 11:27
 */
@Getter
@Setter
public class Email {

    /**
     * 收件人
     */
    private String addressArrStr;

    private InternetAddress[] addressArr;

    private String subject;
    private String content;

    /**
     * 抄送地址
     */
    private InternetAddress[] carbonCopyArr;

    /**
     * 附件集合
     */
    private List<EmailAttachment> attachmentList;

    /**
     * 图片集合
     */
    private List<EmailImage> imageList;

    public Email(InternetAddress[] addressArr, String subject) {
        this.addressArr = addressArr;
        this.subject = subject;
    }

    public String getAddressArrStr() {
        if (addressArrStr == null) {
            addressArrStr = Arrays.toString(addressArr);
        }
        return addressArrStr;
    }
}
