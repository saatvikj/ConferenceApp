package com.example.conferenceapp.utils;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class EmailClient extends AsyncTask<String, Integer, Void> {

    @Override
    protected Void doInBackground(String... strings) {

        String email = strings[0];
        String joining_code = strings[1];

        final String username = "meghnagupta64@gmail.com";
        final String password = "july/31/1998";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "meghna-HP-Pavilion-Notebook.iiitd.edu.in");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });
        try {
            javax.mail.Message message = new javax.mail.internet.MimeMessage(session);
            message.setFrom(new javax.mail.internet.InternetAddress("meghnagupta64@gmail.com"));
            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse("saatvik16261@iiitd.ac.in"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }



}
