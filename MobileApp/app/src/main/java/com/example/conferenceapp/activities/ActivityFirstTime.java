package com.example.conferenceapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.MainApplication;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.EmailClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityFirstTime extends AppCompatActivity {

    Context context;
    EditText emailField;
    Button button;
    Conference conference;
    ProgressDialog progressBar;
    private DatabaseReference mDatabase;
    private String final_email_id;
    private String final_joining_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        context = getApplicationContext();
        emailField = findViewById(R.id.editTextUsername);
        button = findViewById(R.id.joiningCodeButton);
        progressBar = new ProgressDialog(this);
        progressBar.setIndeterminate(true);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Processing information");
        try {
            conference = ConferenceCSVParser.parseCSV(context);

        } catch (Exception e) {

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                final String emailId = emailField.getText().toString();
                final String conference_id = conference.getConference_id();
                emailField.setText("");
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.child(conference_id).child("Users").getChildren()) {
                            User c = d.getValue(User.class);
                            String email = c.getEmail();
                            if (email.equals(emailId)) {
                                progressBar.hide();
                                final_joining_code = c.getJoining_code();
                                final_email_id = email;
                                String[] details = {final_email_id, final_joining_code, c.getName()};
                                new EmailClient().execute(details);

                                button.setText(R.string.joining_code_verify);
                                emailField.setHint(R.string.joining_code);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String user_input = emailField.getText().toString();
                                        if (user_input.equals(final_joining_code)) {
                                            Intent intent = new Intent(ActivityFirstTime.this, ActivitySetPassword.class);
                                            ((MainApplication) getApplication()).setType("paid");
                                            ((MainApplication) getApplication()).setEmail(emailId);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Joining code incorrect!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}