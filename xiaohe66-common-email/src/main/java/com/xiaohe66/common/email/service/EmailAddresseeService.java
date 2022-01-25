package com.xiaohe66.common.email.service;

import com.sun.mail.pop3.POP3SSLStore;
import com.xiaohe66.common.email.bo.EmailAddressee;
import com.xiaohe66.common.email.ex.EmailAcquireException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimePart;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

/**
 * @author xiaohe
 * @time 2020.12.07 14:58
 */
public class EmailAddresseeService {

    private static final Logger log = LoggerFactory.getLogger(EmailAddresseeService.class);

    private EmailAddressee addressee;

    private Session session;

    private URLName urlName;

    public static EmailAddresseeService getInstance(EmailAddressee addressee) {
        return new EmailAddresseeService(addressee);
    }

    private EmailAddresseeService(EmailAddressee addressee) {
        this.addressee = addressee;
        init();
    }

    private void init() {

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", addressee.getPop3Host());
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.port", "995");
        props.put("mail.smtp.auth", "true");

        session = Session.getInstance(props);

        urlName = new URLName("pop3", addressee.getPop3Host(), 995, null, addressee.getEmailAccount(), addressee.getEmailPwd());
    }

    public Inbox acquire() {
        return new Inbox(session, urlName);
    }

    public static class Inbox implements Closeable {

        private Store store;
        private Folder folder;

        private Inbox(Session session, URLName urlName) {
            store = new POP3SSLStore(session, urlName);
            try {
                store.connect();
                // 获取收件箱:
                folder = store.getFolder("INBOX");

            } catch (MessagingException e) {
                throw new EmailAcquireException(e);
            }
        }

        public int getTotalCount() throws MessagingException {
            return folder.getMessageCount();
        }

        public int getNewCount() throws MessagingException {
            return folder.getNewMessageCount();
        }

        public int getUnreadCount() throws MessagingException {
            return folder.getUnreadMessageCount();
        }

        public int getDeletedCount() throws MessagingException {
            return folder.getDeletedMessageCount();
        }

        public Iterator<Parser> getParseIterator() {

            Message[] messages;
            try {
                folder.open(Folder.READ_ONLY);
                messages = folder.getMessages();

            } catch (MessagingException e) {
                throw new EmailAcquireException(e);
            }

            return new Iterator<Parser>() {

                int i;

                @Override
                public boolean hasNext() {
                    return i < messages.length;
                }

                @Override
                public Parser next() {
                    if (i >= messages.length) {
                        throw new NoSuchElementException();
                    }
                    return new Parser(messages[i++]);
                }
            };
        }

        @Override
        public void close() {
            try {
                folder.close(true);

            } catch (MessagingException e) {
                log.error("close folder fails", e);
            }

            try {
                store.close();
            } catch (MessagingException e) {
                log.error("close store fails", e);
            }
        }
    }

    public static class Parser {

        private Message message;

        private String htmlContent;
        private String plainContent;

        private Map<String, DataSource> attachmentMap = new HashMap<>();
        private Map<String, DataSource> imageMap = new HashMap<>();

        private Parser(Message message) {
            this.message = message;
        }

        public String getSubject() throws MessagingException {
            return message.getSubject();
        }

        public Address[] getFrom() throws MessagingException {
            return message.getFrom();
        }

        public Address[] getTo() throws MessagingException {
            return message.getReplyTo();
        }

        public String getContent() throws IOException, MessagingException {
            getContent((MimePart) message);
            return htmlContent == null ? plainContent : htmlContent;
        }

        private void getContent(MimePart part) throws IOException, MessagingException {

            if (htmlContent == null && part.isMimeType("text/html")) {
                htmlContent = (String) part.getContent();

            } else if (plainContent == null && part.isMimeType("text/plain")) {
                plainContent = (String) part.getContent();

            } else if (part.isMimeType("multipart/*")) {
                final Multipart mp = (Multipart) part.getContent();
                final int count = mp.getCount();

                for (int i = 0; i < count; i++) {
                    getContent((MimeBodyPart) mp.getBodyPart(i));
                }
            } else {

                DataSource dataSource = part.getDataHandler().getDataSource();

                String cid = stripContentId(part.getContentID());

                if (cid == null) {
                    // 附件
                    String fileName = part.getFileName();
                    attachmentMap.put(fileName, dataSource);

                    log.debug("fileName : {}", fileName);

                } else {
                    // 图片
                    imageMap.put(cid, dataSource);
                    log.debug("cid : {}", cid);

                }
            }
        }

        private String stripContentId(final String contentId) {
            if (contentId == null) {
                return null;
            }
            return contentId.trim().replaceAll("[\\<\\>]", "");
        }

        public Set<String> getAttachmentNames() {
            return attachmentMap.keySet();
        }

        public InputStream getAttachmentInputStream(String name) throws IOException {
            DataSource dataSource = attachmentMap.get(name);
            if (dataSource == null) {
                throw new NoSuchElementException("no such element : " + name);
            }
            return dataSource.getInputStream();
        }

        public Set<String> getImageCids() {
            return imageMap.keySet();
        }

        public InputStream getImageInputStream(String name) throws IOException {
            DataSource dataSource = imageMap.get(name);
            if (dataSource == null) {
                throw new NoSuchElementException("no such element : " + name);
            }
            return dataSource.getInputStream();
        }
    }

}
