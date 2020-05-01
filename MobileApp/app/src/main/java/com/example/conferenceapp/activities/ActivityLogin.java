package com.example.conferenceapp.activities;

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
import com.example.conferenceapp.models.MainApplication;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ActivityLogin extends AppCompatActivity {

    TextView skip;
    EditText emailEditText;
    EditText passwordEditText;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Button signInButton;
    Conference conference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }
        getSupportActionBar().setTitle(conference.getConference_name());
        Button login = findViewById(R.id.loginButton);
        ImageView testImage = findViewById(R.id.testImageView);

        Glide.with(this).load("http://i.imgur.com/DvpvklR.png").into(testImage);

        emailEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error logging in, try again!", Toast.LENGTH_LONG).show();
                        } else {

                            Intent intent = new Intent(ActivityLogin.this, NavBarActivity.class);
                            ((MainApplication) getApplication()).setType("paid");
                            ((MainApplication) getApplication()).setEmail(email);

                            startActivity(intent);
                        }
                    }
                });


            }
        });

        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, NavBarActivity.class);
                ((MainApplication) getApplication()).setType("skip");
                startActivity(intent);
            }
        });
    }
}
