package com.example.conferenceapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conferenceapp.models.User;
import com.google.firebase.database.*;
import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.utils.ConferenceCSVParser;

import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

public class ActivityFirstTime extends AppCompatActivity {

    Context context;
    EditText emailField;
    Button button;
    Conference conference = null;
    private DatabaseReference mDatabase;
    private String final_email_id;
    private String final_joining_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        emailField = findViewById(R.id.editTextUsername);
        button = findViewById(R.id.joiningCodeButton);

        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailId = emailField.getText().toString();
                final String conference_id = "d52d1b95-ad2d-46de-8145-1844b15792d5";
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.child(conference_id).child("Users").getChildren()){
                            User c = d.getValue(User.class);
                            String email = c.getEmail();
                            if(email.equals(emailId)){
                                final_joining_code = c.getJoining_code();
                                final_email_id = email;
                                Toast.makeText(getApplicationContext(),final_joining_code,Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                final String username = "meghnagupta64@gmail.com";
//                final String password = "july/31/1998";
//
//                Properties props = new Properties();
//                props.put("mail.smtp.auth", "true");
//                props.put("mail.smtp.starttls.enable", "true");
//                props.put("mail.smtp.host", "smtp.gmail.com");
//                props.put("mail.smtp.port", "587");
//
//                Session session = Session.getInstance(props,
//                        new javax.mail.Authenticator() {
//                            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                                return new javax.mail.PasswordAuthentication(username, password);
//                            }
//                        });
//                try {
//                    javax.mail.Message message = new javax.mail.internet.MimeMessage(session);
//                    message.setFrom(new javax.mail.internet.InternetAddress("meghnagupta64@gmail.com"));
//                    message.setRecipients(javax.mail.Message.RecipientType.TO,
//                            InternetAddress.parse("saatvik16261@iiitd.ac.in"));
//                    message.setSubject("Testing Subject");
//                    message.setText("Dear Mail Crawler,"
//                            + "\n\n No spam to my email, please!");
//
//                    MimeBodyPart messageBodyPart = new MimeBodyPart();
//
//                    Multipart multipart = new MimeMultipart();
//
//                    messageBodyPart = new MimeBodyPart();
//                    multipart.addBodyPart(messageBodyPart);
//
//                    message.setContent(multipart);
//
//                    Transport.send(message);
//
//                    Log.d("MESSAGE","Chala gaya hoga");
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}

