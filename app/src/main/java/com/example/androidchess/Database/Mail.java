package com.example.androidchess.Database;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {
    private static Mail mailObject = null;
    private String host = "smtp.gmail.com";
    private String port = "587";
    private final String shackUser = "droidtjack@gmail.com";
    private final String shackPassword = "hkrtjack127";
    private boolean authentication=true;
    private boolean smtpServerTTLSEnabled = true;
    private Session session;


    //Instance
    static Mail getInstance(){
        if (mailObject == null){
            mailObject = new Mail();
        }
        return mailObject;
    }

    //Mail constructor
    private Mail(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", String.valueOf(authentication));
        props.put("mail.smtp.starttls.enable",smtpServerTTLSEnabled);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(shackUser, shackPassword);
            }
        });
    }

    /**
     * Sends email of new password with help of
     * Simple Mail Transfer Protocol + Multi-purpose Internet Mail Extension
     */
    public void sendEmail(String recipientEmail, String recoverPassword, String username){

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(shackUser));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject("Password Recovery");
            message.setText("Hello, " + username + "\nYour new password: " + recoverPassword
            + "\nRegards, " + "\nAndroidChess");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

