package com.xiaohe66.common.email;

import com.xiaohe66.common.email.bo.Email;
import com.xiaohe66.common.email.bo.EmailAttachment;
import com.xiaohe66.common.email.bo.EmailImage;
import com.xiaohe66.common.email.bo.EmailSenderInfo;
import com.xiaohe66.common.email.ex.EmailSendException;
import com.xiaohe66.common.util.Check;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohe
 * @time 2020.03.05 10:51
 */
@Slf4j
public class EmailSender {

    /**
     * 发件人缓存
     * 一般来说，发件人不会怎么换，因此可以缓存下来
     */
    private static final Map<EmailSenderInfo, EmailSender> instanceMap = new ConcurrentHashMap<>();

    private Session session;

    private final EmailSenderInfo sender;

    private EmailSender(EmailSenderInfo sender) {
        this.sender = sender;
        init();
    }

    public static synchronized EmailSender getInstance(@NonNull EmailSenderInfo senderInfo) {
        return instanceMap.computeIfAbsent(senderInfo, key -> new EmailSender(senderInfo));
    }

    private void init() {

        log.info("init email session");
        log.debug("emailSender : {}", sender);

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", sender.getSmtpHost());
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");

        session = Session.getInstance(props);

        log.info("init email session success.");
    }

    public void sendEmail(@NonNull Email email) throws MessagingException {
        log.info("send email, target : {}", email.getAddresses());
        check(email);
        try {
            MimeMessage message = createMimeMessage(email);
            Transport.send(message, sender.getEmailAccount(), sender.getEmailPwd());
            log.info("send email success, target : {}", email.getAddresses());

        } catch (SendFailedException e) {

            log.warn("send email fail, address is wrong, error message : {}, target : {}", e.getMessage(), email.getAddresses());
            if (email.isTryAgainSend()) {
                handleSendFailedException(e, email);
            }

        } catch (MessagingException e) {
            log.error("send email fail, cannot connect network, message : {}, target : {}", e.getMessage(), email.getAddresses());
            throw e;

        } catch (Exception e) {
            log.error("send email fail, unknown exception, error message : {}, target : {}", e.getMessage(), email.getAddresses());
            throw new EmailSendException(e);
        }
    }

    protected void handleSendFailedException(SendFailedException e, Email email) {

        InternetAddress[] invalidAddresses = castToInternetAddress(e.getInvalidAddresses());
        InternetAddress[] validSentAddresses = castToInternetAddress(e.getValidSentAddresses());
        InternetAddress[] validUnsentAddresses = castToInternetAddress(e.getValidUnsentAddresses());

        if (log.isWarnEnabled()) {

            log.warn("invalidAddresses : {}, validSentAddresses : {}, validUnsentAddresses : {}",
                    EmailHelper.joinAddressString(invalidAddresses),
                    EmailHelper.joinAddressString(validSentAddresses),
                    EmailHelper.joinAddressString(validUnsentAddresses));
        }

        if (validUnsentAddresses.length == 0) {
            log.warn("not have validSentAddresses, so don`t try send.");
            return;
        }

        InternetAddress[] targetAddressArr = email.getAddressArr();
        List<InternetAddress> targetAddressList = new ArrayList<>(targetAddressArr.length);
        Collections.addAll(targetAddressList, targetAddressArr);

        InternetAddress[] carbonCopyArr = email.getCcArr();
        List<InternetAddress> cssList = new ArrayList<>(carbonCopyArr.length);
        Collections.addAll(cssList, carbonCopyArr);

        // 异常信息中，错误的邮箱地址是包含了收件人和抄送人的，
        // 但无法判断是属于哪一个，因此需要从收件人和抄送人中都检查并删除错误的邮箱地址
        for (InternetAddress invalidAddress : invalidAddresses) {
            targetAddressList.remove(invalidAddress);
            cssList.remove(invalidAddress);
        }

        email.setAddressArr(targetAddressList.toArray(new InternetAddress[0]));
        email.setCcArr(cssList.toArray(new InternetAddress[0]));

        try {
            MimeMessage message = createMimeMessage(email);

            log.warn("try send email again");

            Transport.send(message, sender.getEmailAccount(), sender.getEmailPwd());

            log.warn("try send email again success");

        } catch (Exception e1) {

            log.error("try send email again fail, error message : {}", e1.getMessage());
            throw new EmailSendException(e1);
        }
    }

    protected InternetAddress[] castToInternetAddress(Address[] addresses) {
        if (addresses == null) {
            return new InternetAddress[0];
        }
        InternetAddress[] result = new InternetAddress[addresses.length];
        for (int i = 0; i < addresses.length; i++) {
            result[i] = (InternetAddress) addresses[i];
        }
        return result;
    }

    protected void check(Email email) {

        if (ArrayUtils.isEmpty(email.getAddressArr()) || StringUtils.isEmpty(email.getAddresses())) {
            throw new IllegalArgumentException("address cannot be empty");
        }

        if (email.getContent() == null) {
            throw new IllegalArgumentException("content cannot be null");
        }
    }

    protected MimeMessage createMimeMessage(Email email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = new MimeMessage(session);

        message.setSubject(email.getSubject());
        message.setFrom(new InternetAddress(sender.getEmailAccount(), sender.getEmailUserName(), StandardCharsets.UTF_8.name()));
        message.setRecipients(Message.RecipientType.TO, email.getAddressArr());

        // 抄送
        if (email.getCcArr() != null) {
            message.setRecipients(Message.RecipientType.CC, email.getCcArr());

        } else if (StringUtils.isNotEmpty(email.getCcs())) {
            message.setRecipients(Message.RecipientType.CC, EmailHelper.splitAddress(email.getCcs()));
        }

        // 混合消息体
        MimeMultipart mime = new MimeMultipart("mixed");

        addAttachment(mime, email.getAttachmentList());
        addContent(mime, email.getContent(), email.getImageList());

        message.setContent(mime);
        message.setSentDate(new Date());

        return message;
    }

    protected void addAttachment(MimeMultipart mime, List<EmailAttachment> attachmentList) throws MessagingException {

        if (attachmentList != null) {
            for (EmailAttachment emailAttachment : attachmentList) {
                BodyPart attachment = EmailHelper.buildAttachmentBodyPart(emailAttachment);

                mime.addBodyPart(attachment);
            }
        }
    }

    protected void addContent(MimeMultipart mime, String html, List<EmailImage> imageList) throws MessagingException {

        MimeMultipart content = new MimeMultipart("related");

        BodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(html, "text/html;charset=utf-8");

        content.addBodyPart(htmlBodyPart);

        if (Check.isNotEmpty(imageList)) {
            for (EmailImage emailImage : imageList) {

                BodyPart imagePart = EmailHelper.buildImageBodyPart(emailImage);
                content.addBodyPart(imagePart);
            }
        }

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content);

        mime.addBodyPart(mimeBodyPart);
    }
}
