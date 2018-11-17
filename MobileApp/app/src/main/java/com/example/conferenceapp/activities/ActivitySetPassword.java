package com.example.conferenceapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.User;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        String email = getIntent().getStringExtra("email");

        newPasswordField = findViewById(R.id.editTextNewPassword);
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword);

        enteredPassword = newPasswordField.getText().toString();
        confirmedPassword = confirmPasswordField.getText().toString();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enteredPassword.equals(confirmedPassword)){
                    final String conference_id = "d52d1b95-ad2d-46de-8145-1844b15792d5";
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot d: dataSnapshot.child(conference_id).child("Users").getChildren()){
                                User c = d.getValue(User.class);
                                String email = c.getEmail();
                                c.setPassword(enteredPassword);
                                c.setPassword(confirmedPassword);

                                mDatabase.setValue(c);
                                Intent intent = new Intent(ActivitySetPassword.this, NavBarActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }
}
