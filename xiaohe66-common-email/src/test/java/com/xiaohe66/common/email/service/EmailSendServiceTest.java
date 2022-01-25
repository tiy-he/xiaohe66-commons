package com.xiaohe66.common.email.service;

import com.xiaohe66.common.email.EmailSendService;
import com.xiaohe66.common.email.bo.Email;
import com.xiaohe66.common.email.bo.EmailAttachment;
import com.xiaohe66.common.email.bo.EmailImage;
import com.xiaohe66.common.email.bo.EmailSenderInfo;
import com.xiaohe66.common.email.EmailHelper;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmailSendServiceTest {

    public static void main(String[] args) throws MessagingException {

        String pwd = "";

        EmailSenderInfo emailSender = new EmailSenderInfo("tingyuanhe@ismartgo.com", "ty", pwd, "smtp.exmail.qq.com");
        EmailSendService emailSendService = EmailSendService.getInstance(emailSender);

        Email email = new Email(EmailHelper.splitAddress("tingyuanhe@ismartgo.com"), "测试主题");

        List<EmailAttachment> attachmentList = Arrays.asList(
                new EmailAttachment("文件1.txt", new File("C:\\Users\\Xiao He\\Desktop\\1.txt")),
                new EmailAttachment("文件2.txt", new File("C:\\Users\\Xiao He\\Desktop\\2.txt"))
        );

        email.setAttachmentList(attachmentList);
        email.setImageList(Collections.singletonList(new EmailImage("http://test.com/1.png",new File("C:\\Users\\Xiao He\\Desktop\\1.png"))));

        email.setContent("内容<br>换行<img src=\"http://test.com/1.png\">");

        emailSendService.sendEmail(email);


    }
}