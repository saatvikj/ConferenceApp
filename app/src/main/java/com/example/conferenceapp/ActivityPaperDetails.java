package com.example.conferenceapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityPaperDetails extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        LinearLayout speakerList = (LinearLayout) findViewById(R.id.speakerListLayout);
        for(int i = 0; i < 4; i++){
            View inflatedView = getLayoutInflater().inflate(R.layout.inflator_speaker_list, null);
            TextView nameTextView = inflatedView.findViewById(R.id.name);
            nameTextView.setText("Meghna Gupta");
            TextView bioTextView = inflatedView.findViewById(R.id.bio);
            bioTextView.setText("blah blah blah");
            speakerList.addView(inflatedView);
        }
    }
}
