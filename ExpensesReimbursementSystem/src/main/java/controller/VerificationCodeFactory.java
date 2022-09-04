package controller;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class VerificationCodeFactory {

    public static int sendEmail(String email){

        // Sender's email
        String from ="******@gmail.com" ;

        //Receiver email
        String to = email;

        //Generate random code
        Random random = new Random();
        int verificationCode = random.nextInt(10000, 99999);

        // Gmail SMTP(Simple Mail Transfer Protocol) server address
        String host = "smtp.gmail.com";

        //used to maintain lists of values in which the key is a String and the value is also a String
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        //Secure Sockets Layer (SSL) is a standard security technology for establishing an encrypted link between a server and a client
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                //google will provide a password for Gmail account
                //https://support.google.com/accounts/answer/185833?hl=en
                return new PasswordAuthentication(from, "*******");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            //header field of the header.
            message.setFrom(new InternetAddress(from));

            // header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // header field
            message.setSubject("Verification Code!");

            // actual message
            message.setText("" + verificationCode);

            System.out.println("sending...");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return verificationCode;
    }
}
