package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;

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
        getSupportActionBar().setTitle("Why Matters Matter");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guide_home) {
            Intent intent = new Intent(ActivityPaperDetails.this, NavBarActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
