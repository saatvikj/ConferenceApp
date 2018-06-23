package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.utils.ConferenceCSVParser;

public class ActivityLogin extends AppCompatActivity {

    TextView skip;

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
