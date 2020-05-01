package com.example.conferenceapp.activities;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;
    Conference conference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        final String email = ((MainApplication) getApplication()).getEmail();

        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }

        newPasswordField = findViewById(R.id.editTextNewPassword);
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword);


        verifyButton = findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredPassword = newPasswordField.getText().toString();
                confirmedPassword = confirmPasswordField.getText().toString();

                newPasswordField.setText("");
                confirmPasswordField.setText("");

                if(enteredPassword.equals(confirmedPassword)){
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Error, try again.",Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(ActivitySetPassword.this, ActivityMyProfile.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"Passwords don't match!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}