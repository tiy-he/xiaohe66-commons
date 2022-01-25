package com.xiaohe66.common.email.bo;

import com.xiaohe66.common.email.EmailHelper;
import lombok.Data;
import lombok.NonNull;

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

    private final String subject;
    private final String content;

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

    /**
     * 在地址存在错误时, 是否清楚错误后重新发送一次，默认为 true
     */
    private boolean tryAgainSend = true;

    public Email(@NonNull String subject, String addresses, String content) {
        this.subject = subject;
        this.addresses = addresses;
        this.content = content;
    }

    public Email(@NonNull String subject, InternetAddress[] addressArr, String content) {
        this.subject = subject;
        this.addressArr = addressArr;
        this.content = content;
    }

    public String getAddresses() {
        if (addresses == null && addressArr != null) {
            addresses = EmailHelper.joinAddressString(addressArr);
        }
        return addresses;
    }

    public String getCcs() {
        if (ccs == null && ccArr != null) {
            ccs = EmailHelper.joinAddressString(ccArr);
        }
        return ccs;
    }
}
