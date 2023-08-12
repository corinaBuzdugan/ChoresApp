package org.example;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailManager {
    private String senderEmail;
    private String senderPassword;
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;

    public EmailManager(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }

    public void sendWeeklyReport(String recipientEmail, String reportContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Weekly Report for Your Child's Chores");

            // Use the provided reportContent variable for the email content
            String content = reportContent;

            message.setText(content);

            Transport.send(message);

            System.out.println("Weekly report email sent to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send the weekly report email.");
        }
    }

    public void sendEmailWithAttachment(String recipientEmail, String subject, String body, String filePath) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            if (filePath != null && !filePath.isEmpty()) {
                addAttachment(multipart, filePath);
            }

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email with attachment sent to: " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send the email with attachment.");
        }
    }

    private void addAttachment(Multipart multipart, String filePath) throws MessagingException {
        BodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(source.getName());
        multipart.addBodyPart(attachmentBodyPart);
    }
}
