package com.xiaohe66.common.email.bo;

/**
 * 用于收件箱
 *
 * @author xiaohe
 * @time 2020.12.07 14:57
 */
public class EmailAddressee {

    private String pop3Host;
    private String emailAccount;
    private String emailPwd;

    public EmailAddressee(String pop3Host, String emailAccount, String emailPwd) {
        this.pop3Host = pop3Host;
        this.emailAccount = emailAccount;
        this.emailPwd = emailPwd;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public String getEmailPwd() {
        return emailPwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailAddressee that = (EmailAddressee) o;

        if (pop3Host != null ? !pop3Host.equals(that.pop3Host) : that.pop3Host != null) {
            return false;
        }
        if (emailAccount != null ? !emailAccount.equals(that.emailAccount) : that.emailAccount != null) {
            return false;
        }
        return emailPwd != null ? emailPwd.equals(that.emailPwd) : that.emailPwd == null;
    }

    @Override
    public int hashCode() {
        int result = pop3Host != null ? pop3Host.hashCode() : 0;
        result = 31 * result + (emailAccount != null ? emailAccount.hashCode() : 0);
        result = 31 * result + (emailPwd != null ? emailPwd.hashCode() : 0);
        return result;
    }
}
