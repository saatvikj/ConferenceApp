package com.example.conferenceapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityLogin extends AppCompatActivity {

    TextView skip;
    EditText emailEditText;
    EditText passwordEditText;
    private DatabaseReference mDatabase;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }
        getSupportActionBar().setTitle(conference.getConference_name());
        Button login = findViewById(R.id.loginButton);
        ImageView testImage = findViewById(R.id.testImageView);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, NavBarActivity.class);
                intent.putExtra("Source", "normal");
                startActivity(intent);
            }
        });

        Glide.with(this).load("http://i.imgur.com/DvpvklR.png").into(testImage);

        final String email = emailEditText.getText().toString();
        final String password = emailEditText.getText().toString();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String conference_id = "d52d1b95-ad2d-46de-8145-1844b15792d5";
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.child(conference_id).child("Users").getChildren()){
                            User c = d.getValue(User.class);
                            String input_email = c.getEmail();
                            String input_pass = c.getPassword();

                            if(input_email.equals(email) && input_pass.equals(password)){
                                Intent intent = new Intent(ActivityLogin.this, NavBarActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Check credentials again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, NavBarActivity.class);
                intent.putExtra("Source", "skip");
                startActivity(intent);
            }
        });
    }
}
