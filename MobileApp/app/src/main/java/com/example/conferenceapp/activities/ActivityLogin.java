package com.example.conferenceapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Logging In");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseAsyncTask().execute();
            }
        });

        skip = findViewById(R.id.sign_up);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityFirstTime.class);
                startActivity(intent);
            }
        });
    }


    class FirebaseAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            final String conference_id = conference.getConference_id();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for(DataSnapshot d: dataSnapshot.child(conference_id).child("Users").getChildren()){
                        User c = d.getValue(User.class);
                        String input_email = c.getEmail();
                        String input_pass = c.getPassword();

                        if(input_email.equals(email) && input_pass.equals(password)){
                            progressDialog.hide();
                            Intent intent = new Intent(ActivityLogin.this, NavBarActivity.class);
                            intent.putExtra("Source", "paid");
                            intent.putExtra("email", input_email);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        else if(input_email.equals(email)){
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(),"Incorrect credentials provided, try again!" , Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
