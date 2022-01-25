package com.xiaohe66.common.email.bo;

import com.xiaohe66.common.email.EmailHelper;
import lombok.Data;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * 表示一封邮件
 * <p>
 * 包含收件人、抄送人、主题、内容、附件等字段
 *
 * @author xiaohe
 * @time 2020.03.05 11:27
 */
@Data
public class Email {

    /**
     * 收件人, 用逗号隔开
     */
    private String addresses;

    /**
     * 收件人是可以自定义名称的，因此使用这个
     */
    private InternetAddress[] addressArr;

    private String subject;
    private String content;

    /**
     * 抄送地址
     */
    private String ccs;

    /**
     * 抄送地址
     */
    private InternetAddress[] ccArr;

    /**
     * 附件集合
     */
    private List<EmailAttachment> attachmentList;

    /**
     * 图片集合
     */
    private List<EmailImage> imageList;

    public Email(String addresses, String subject) {
        this.addresses = addresses;
        this.subject = subject;
    }

    public Email(InternetAddress[] addressArr, String subject) {
        this.addressArr = addressArr;
        this.subject = subject;
    }

    public String getAddresses() {
        if (addresses == null) {
            addresses = EmailHelper.joinAddressString(addressArr);
        }
        return addresses;
    }

    public String getCcs() {
        if (ccs == null) {
            ccs = EmailHelper.joinAddressString(ccArr);
        }
        return ccs;
    }
}
