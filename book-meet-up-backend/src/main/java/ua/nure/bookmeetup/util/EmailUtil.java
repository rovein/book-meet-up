package ua.nure.bookmeetup.util;

import com.google.common.io.Files;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Date;
import java.util.Properties;

@Log4j2
public class EmailUtil {

    private static final String USER_NAME = System.getProperty("EMAIL_USER_NAME");
    private static final String PASSWORD = System.getProperty("EMAIL_PASSWORD");

    public static EmailBuilder message() {
        return new EmailBuilder();
    }

    public static Properties properties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return props;
    }

    public static Authenticator authenticator() {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        };
    }

    @SneakyThrows
    public static String retrieveContentFromHtmlTemplate(String pathToTemplate) {
        Path filePath = PathsUtil.getResourcePath(pathToTemplate);
        return Files.asCharSource(new File(filePath.toString()), StandardCharsets.UTF_8).read();
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = createMessage(session, subject, toEmail);
            msg.setContent(body, "text/html; charset=utf-8");
            Transport.send(msg);
            log.info("Email to {} was sent successfully", toEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error while sending an email to receiver " + toEmail, e);
        }
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body, String pathToAttachment) {
        try {
            MimeMessage msg = createMessage(session, subject, toEmail);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            int lastSlash = pathToAttachment.lastIndexOf('/');
            String filename = pathToAttachment.substring(lastSlash + 1);
            DataSource source = new FileDataSource(pathToAttachment);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
            log.info("Email to {} was sent successfully", toEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error while sending an email to receiver " + toEmail, e);
        }
    }

    private static MimeMessage createMessage(Session session, String subject, String emailTo)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/html; charset=utf-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress("admin@book-meet-up.com", "BookMeetUp"));
        msg.setReplyTo(InternetAddress.parse(USER_NAME, false));
        msg.setSubject(subject, "UTF-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));

        return msg;
    }

    public static class EmailBuilder {

        private final Session session = Session.getInstance(EmailUtil.properties(), EmailUtil.authenticator());

        private String toEmail;

        private String subject;

        private String body;

        private String pathToAttachment;

        public EmailBuilder() {
        }

        public EmailBuilder destination(String toEmail) {
            this.toEmail = toEmail;
            return this;
        }

        public EmailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder body(String body) {
            this.body = body;
            return this;
        }

        public EmailBuilder pathToAttachment(String pathToAttachment) {
            this.pathToAttachment = pathToAttachment;
            return this;
        }

        public void send() {
            sendEmail(session, toEmail, subject, body);
        }

        public void sendWithAttachment() {
            sendEmail(session, toEmail, subject, body, pathToAttachment);
        }
    }

}

