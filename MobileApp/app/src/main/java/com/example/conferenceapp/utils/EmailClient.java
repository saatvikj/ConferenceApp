package com.example.conferenceapp.utils;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.Properties;

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

public class EmailClient extends AsyncTask<String, Integer, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        String email = strings[0];
        String joining_code = strings[1];



        final String username = "jain.saatvik3@gmail.com";

        final String APIKey = "a018ea6fbacf38cabd8633bb7939fa7e";
        final String SecretKey = "93b17292c7805c64147b2e2c73c94cc9";

        Properties props = new Properties ();

        props.put ("mail.smtp.host", "in.mailjet.com");
        props.put ("mail.smtp.auth", "true");
        props.put ("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance (props,
                new javax.mail.Authenticator ()
                {
                    protected PasswordAuthentication getPasswordAuthentication ()
                    {
                        return new PasswordAuthentication (APIKey, SecretKey);
                    }
                });

        try
        {

            Message message = new MimeMessage(session);
            message.setFrom (new InternetAddress (username));
            message.setRecipients (Message.RecipientType.TO, InternetAddress.parse("jain.saatvik3@gmail.com"));
            message.setSubject ("Your mail from Mailjet");
            message.setText (Arrays.toString(strings));

            Transport.send (message);

        }
        catch (MessagingException e)
        {
            throw new RuntimeException (e);
        }

        return null;
    }
}
