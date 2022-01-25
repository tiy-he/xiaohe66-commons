package com.xiaohe66.common.email.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 邮件发件人
 *
 * @author xiaohe
 * @time 2020.03.04 15:11
 */
@Data
@AllArgsConstructor
public class EmailSenderInfo {

    private String smtpHost;
    private String emailAccount;
    private String emailUserName;
    private String emailPwd;
}
