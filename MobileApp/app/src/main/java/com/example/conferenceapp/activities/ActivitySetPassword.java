package com.example.conferenceapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivitySetPassword extends AppCompatActivity {

    EditText newPasswordField;
    EditText confirmPasswordField;
    Button verifyButton;
    String enteredPassword;
    String confirmedPassword;
    private DatabaseReference mDatabase;
    Conference conference = null;
    ProgressDialog progressDialog;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        email = getIntent().getStringExtra("email");

        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }

        newPasswordField = findViewById(R.id.editTextNewPassword);
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Resetting Password");

        verifyButton = (Button) findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseAsyncTask().execute();
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
            enteredPassword = newPasswordField.getText().toString();
            confirmedPassword = confirmPasswordField.getText().toString();
            if (enteredPassword.equals(confirmedPassword)) {
                final String conference_id = conference.getConference_id();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.child(conference_id).child("Users").getChildren()) {
                            User c = d.getValue(User.class);
                            if (email.equals(c.getEmail())) {
                                progressDialog.hide();
                                c.setPassword(enteredPassword);
                                mDatabase.child(conference_id).child("Users").child(d.getKey()).setValue(c);
                                Intent intent = new Intent(ActivitySetPassword.this, ActivityMyProfile.class);
                                intent.putExtra("Source", "paid");
                                intent.putExtra("email", email);
                                startActivity(intent);

                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
