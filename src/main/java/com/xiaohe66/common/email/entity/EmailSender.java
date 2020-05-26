package com.xiaohe66.common.email.entity;

/**
 * 邮件发件人
 *
 * @author xiaohe
 * @time 2020.03.04 15:11
 */
public class EmailSender {

    private String smtpHost;
    private String emailAccount;
    private String emailUserName;
    private String emailPwd;

    public EmailSender(String emailAccount, String emailUserName, String emailPwd, String smtpHost) {
        this.emailAccount = emailAccount;
        this.emailUserName = emailUserName;
        this.emailPwd = emailPwd;
        this.smtpHost = smtpHost;
    }


    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getEmailUserName() {
        return emailUserName;
    }

    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }

    public String getEmailPwd() {
        return emailPwd;
    }

    public void setEmailPwd(String emailPwd) {
        this.emailPwd = emailPwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailSender that = (EmailSender) o;

        if (!smtpHost.equals(that.smtpHost)) {
            return false;
        }
        if (!emailAccount.equals(that.emailAccount)) {
            return false;
        }
        if (!emailUserName.equals(that.emailUserName)) {
            return false;
        }
        return emailPwd.equals(that.emailPwd);
    }

    @Override
    public int hashCode() {
        int result = smtpHost.hashCode();
        result = 31 * result + emailAccount.hashCode();
        result = 31 * result + emailUserName.hashCode();
        result = 31 * result + emailPwd.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" + "\"smtpHost\":\"" + smtpHost + "\""
                + ",\"emailAccount\":\"" + emailAccount + "\""
                + ",\"emailUserName\":\"" + emailUserName + "\""
                + ",\"emailPwd\":\"" + emailPwd + "\""
                + "}";
    }
}
