package com.xiaohe66.common.email.service;

import com.xiaohe66.common.email.bo.EmailAddressee;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

public class EmailAddresseeServiceTest {

    private static final Logger log = LoggerFactory.getLogger(EmailAddresseeServiceTest.class);

    public static void main(String[] args) {

        EmailAddressee addressee = new EmailAddressee("pop.exmail.qq.com", "test@xiaohe66.com", "htyHTY123");

        EmailAddresseeService service = EmailAddresseeService.getInstance(addressee);

        try (EmailAddresseeService.Inbox inbox = service.acquire()) {

            Iterator<EmailAddresseeService.Parser> iterator = inbox.getParseIterator();
            while (iterator.hasNext()) {
                EmailAddresseeService.Parser parser = iterator.next();
                try {
                    String subject = parser.getSubject();
                    String from = toStringAddress(parser.getFrom());
                    String to = toStringAddress(parser.getTo());
                    String content = parser.getContent();

                    log.debug("subject : {}", subject);
                    log.debug("from : {}", from);
                    log.debug("to : {}", to);
                    log.debug("content : {}", content);

                    Set<String> names = parser.getAttachmentNames();
                    for (String name : names) {
                        InputStream inputStream = parser.getAttachmentInputStream(name);

                        File file = new File("D:\\" + name);

                        FileUtils.copyToFile(inputStream, file);
                    }

                    Set<String> cids = parser.getImageCids();
                    for (String cid : cids) {
                        InputStream inputStream = parser.getImageInputStream(cid);

                        File file = new File("D:\\" + cid);

                        FileUtils.copyToFile(inputStream, file);
                    }

                } catch (MessagingException | IOException e) {
                    log.error("邮件系统发生异常", e);
                }
            }
        }
    }

    private static String toStringAddress(Address[] addresses) {

        StringBuilder stringBuilder = new StringBuilder();
        for (Address address : addresses) {
            InternetAddress add = (InternetAddress) address;
            stringBuilder.append(',').append(add.getAddress());
        }

        return stringBuilder.toString();
    }

}