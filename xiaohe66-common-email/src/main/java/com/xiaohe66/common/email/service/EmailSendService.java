package com.xiaohe66.common.email.service;

import com.xiaohe66.common.email.entity.Email;
import com.xiaohe66.common.email.entity.EmailAttachment;
import com.xiaohe66.common.email.entity.EmailImage;
import com.xiaohe66.common.email.entity.EmailSender;
import com.xiaohe66.common.email.ex.EmailSendException;
import com.xiaohe66.common.email.util.EmailUtils;
import com.xiaohe66.common.util.Check;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author xiaohe
 * @time 2020.03.05 10:51
 */
@Slf4j
public class EmailSendService {

    private static Map<EmailSender, EmailSendService> instanceMap = new HashMap<>();

    private Session session;

    private EmailSender sender;

    private EmailSendService(EmailSender sender) {
        this.sender = sender;
        init();
    }

    public static synchronized EmailSendService getInstance(EmailSender sender) {
        if (sender == null) {
            throw new IllegalArgumentException("发件人不能为空");
        }

        return instanceMap.computeIfAbsent(sender, key -> new EmailSendService(sender));
    }

    private void init() {

        log.info("初始化邮件发送服务session");
        log.debug("emailSender : {}", sender);

        // todo : 升级到 jdk 11后报错，注释后还可以使用，先观察暂不处理
//        Security.addProvider(new Provider());

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", sender.getSmtpHost());
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");

        session = Session.getInstance(props);

        log.info("邮件发送服务session初始化完成");
    }

    public void sendEmail(Email email) throws MessagingException {
        log.info("发送邮件,目标 : {}", email.getAddressArrStr());
        check(email);
        try {
            MimeMessage message = createMimeMessage(email);
            Transport.send(message, sender.getEmailAccount(), sender.getEmailPwd());
            log.info("邮件发送成功,目标 : {}", email.getAddressArrStr());

        } catch (SendFailedException e) {
            log.warn("邮件目标地址无效, message : {}", e.getMessage());
            handleSendFailedException(e, email);

        } catch (MessagingException e) {
            log.error("网络连接失败, message : {}, 目标 : {}", e.getMessage(), email.getAddressArrStr());
            throw e;

        } catch (Exception e) {
            log.error("邮件发送失败, message : {}, 目标 : {}", e.getMessage(), email.getAddressArrStr());
            throw new EmailSendException(e);
        }
    }


    private void handleSendFailedException(SendFailedException e, Email email) {

        log.warn("存在错误的邮箱地址");

        InternetAddress[] invalidAddresses = castToInternetAddress(e.getInvalidAddresses());
        InternetAddress[] validSentAddresses = castToInternetAddress(e.getValidSentAddresses());
        InternetAddress[] validUnsentAddresses = castToInternetAddress(e.getValidUnsentAddresses());

        if (log.isWarnEnabled()) {

            log.warn("错误的邮箱地址 : {}", joinAddressString(invalidAddresses));

            log.warn("正确已发送的邮箱地址 : {}", joinAddressString(validSentAddresses));

            log.warn("正确未发送的邮箱地址 : {}", joinAddressString(validUnsentAddresses));
        }

        if (validUnsentAddresses.length == 0) {
            log.warn("没有正确未发送的邮箱地址，因而忽略");
            return;
        }

        InternetAddress[] targetAddressArr = email.getAddressArr();
        List<InternetAddress> targetAddressList = new ArrayList<>(targetAddressArr.length);
        Collections.addAll(targetAddressList, targetAddressArr);

        InternetAddress[] carbonCopyArr = email.getCarbonCopyArr();
        List<InternetAddress> cssList = new ArrayList<>(carbonCopyArr.length);
        Collections.addAll(cssList, carbonCopyArr);

        // 异常信息中，错误的邮箱地址是包含了收件人和抄送人的，
        // 但无法判断是属于哪一个，因此需要从收件人和抄送人中都检查并删除错误的邮箱地址
        for (InternetAddress invalidAddress : invalidAddresses) {
            targetAddressList.remove(invalidAddress);
            cssList.remove(invalidAddress);
        }

        email.setAddressArr(targetAddressList.toArray(new InternetAddress[0]));
        email.setCarbonCopyArr(cssList.toArray(new InternetAddress[0]));

        try {
            MimeMessage message = createMimeMessage(email);

            log.warn("尝试重新发送");
            Transport.send(message, sender.getEmailAccount(), sender.getEmailPwd());
            log.warn("邮件发送成功");

        } catch (Exception e1) {

            log.error("邮件发送异常，经过处理后仍无法正常发送, message : {}", e1.getMessage());
            throw new EmailSendException(e1);
        }
    }

    private String joinAddressString(InternetAddress[] addresses) {
        if (addresses == null) {
            return "";
        }
        StringBuilder addressStr = new StringBuilder();
        for (InternetAddress address : addresses) {
            addressStr.append(",").append(address.getAddress());
        }
        return addressStr.length() > 0 ? addressStr.substring(1) : "";
    }

    private InternetAddress[] castToInternetAddress(Address[] addresses) {
        if (addresses == null) {
            return new InternetAddress[0];
        }
        InternetAddress[] result = new InternetAddress[addresses.length];
        for (int i = 0; i < addresses.length; i++) {
            result[i] = (InternetAddress) addresses[i];
        }
        return result;
    }

    private void check(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
        if (email.getAddressArr() == null || email.getAddressArr().length == 0) {
            throw new IllegalArgumentException("targetAddressArr cannot be empty");
        }

        if (email.getSubject() == null) {
            throw new IllegalArgumentException("subject cannot be null");
        }

        if (email.getContent() == null) {
            throw new IllegalArgumentException("content cannot be null");
        }
    }

    private MimeMessage createMimeMessage(Email email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = new MimeMessage(session);

        message.setSubject(email.getSubject());
        message.setFrom(new InternetAddress(sender.getEmailAccount(), sender.getEmailUserName(), StandardCharsets.UTF_8.name()));
        message.setRecipients(Message.RecipientType.TO, email.getAddressArr());

        // 抄送
        if (email.getCarbonCopyArr() != null) {
            message.setRecipients(Message.RecipientType.CC, email.getCarbonCopyArr());
        }

        // 混合消息体
        MimeMultipart mime = new MimeMultipart("mixed");

        addAttachment(mime, email.getAttachmentList());
        addContent(mime, email.getContent(), email.getImageList());

        message.setContent(mime);
        message.setSentDate(new Date());

        return message;
    }

    private void addAttachment(MimeMultipart mime, List<EmailAttachment> attachmentList) throws MessagingException {

        if (attachmentList != null) {
            for (EmailAttachment emailAttachment : attachmentList) {
                BodyPart attachment = EmailUtils.buildAttachmentBodyPart(emailAttachment);

                mime.addBodyPart(attachment);
            }
        }
    }

    private void addContent(MimeMultipart mime, String html, List<EmailImage> imageList) throws MessagingException {

        MimeMultipart content = new MimeMultipart("related");

        BodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(html, "text/html;charset=utf-8");

        content.addBodyPart(htmlBodyPart);

        if (Check.isNotEmpty(imageList)) {
            for (EmailImage emailImage : imageList) {

                BodyPart imagePart = EmailUtils.buildImageBodyPart(emailImage);
                content.addBodyPart(imagePart);
            }
        }

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content);

        mime.addBodyPart(mimeBodyPart);
    }
}
