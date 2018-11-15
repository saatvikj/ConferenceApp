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
import com.example.conferenceapp.utils.EmailClient;
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
                String[] details = {final_email_id, final_joining_code};
                new EmailClient().execute(details);

            }
        });
    }
}