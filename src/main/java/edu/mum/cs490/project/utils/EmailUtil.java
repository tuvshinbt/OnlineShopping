package edu.mum.cs490.project.utils;

import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Tamir Ganbat 05/01/2018
 */
@Component
public class EmailUtil {

    private static Properties properties = new Properties();
    private static Properties propertiesOfMail = new Properties();

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static{
        try {
            //load a properties file from class path, inside static method
            propertiesOfMail.load(EmailUtil.class.getClassLoader().getResourceAsStream("mail.properties"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        properties.setProperty("mail.smtp.host", propertiesOfMail.getProperty("mail.smtp.host"));
        properties.setProperty("mail.smtp.port", propertiesOfMail.getProperty("mail.smtp.port"));
        properties.setProperty("mail.smtp.auth", propertiesOfMail.getProperty("mail.smtp.auth"));
        properties.setProperty("mail.smtp.starttls.enable", propertiesOfMail.getProperty("mail.smtp.starttls.enable"));
    }

    private final static Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(propertiesOfMail.getProperty("smtp.api.username"), propertiesOfMail.getProperty("smtp.api.password"));
        }
    };
    private final static Session session = Session.getInstance(properties, auth);

    /**
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail(String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = generateMime();
            msg.setSubject(subject, propertiesOfMail.getProperty("mimemessage.subject"));
            msg.setContent(body, propertiesOfMail.getProperty("mimemessage.body"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, Boolean.parseBoolean(propertiesOfMail.getProperty("mimemessage.replyto.internetaddress.strict"))));

            Transport.send(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param toEmail
     * @param subject
     * @param body
     * @param attachmentFile
     */
    public void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] attachmentFile, String nameOfAttachment){
        try
        {
            MimeMessage msg = generateMime();
            msg.setSubject(subject, propertiesOfMail.getProperty("mimemessage.subject"));

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, propertiesOfMail.getProperty("mimemessage.body"));

            MimeBodyPart att = new MimeBodyPart();
            BufferedDataSource bds = new BufferedDataSource(attachmentFile, nameOfAttachment);
            att.setDataHandler(new DataHandler(bds));
            att.setFileName(bds.getName());

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(att);

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);

            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, Boolean.parseBoolean(propertiesOfMail.getProperty("mimemessage.replyto.internetaddress.strict"))));

            Transport.send(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MimeMessage generateMime(){
        MimeMessage msg = new MimeMessage(session);
        try
        {
            msg.addHeader("Content-type", propertiesOfMail.getProperty("Content-type"));
            msg.addHeader("format", propertiesOfMail.getProperty("format"));
            msg.addHeader("Content-Transfer-Encoding", propertiesOfMail.getProperty("Content-Transfer-Encoding"));

            msg.setFrom(new InternetAddress(propertiesOfMail.getProperty("mimemessage.from.internetaddress.address"), propertiesOfMail.getProperty("mimemessage.from.internetaddress.personal")));
            msg.setReplyTo(InternetAddress.parse(propertiesOfMail.getProperty("mimemessage.replyto.internetaddress.addresslist"), Boolean.parseBoolean(propertiesOfMail.getProperty("mimemessage.replyto.internetaddress.strict"))));

            msg.setSentDate(new Date());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}
