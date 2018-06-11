package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Paper;

import org.w3c.dom.Text;

import java.io.Serializable;

public class ActivityPaperDetails extends AppCompatActivity implements Serializable{

    TextView title;
    TextView time;
    TextView location;
    TextView paper_abstract;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        LinearLayout speakerList = (LinearLayout) findViewById(R.id.speakerListLayout);
        Intent intent = getIntent();
        Paper paper = (Paper) intent.getSerializableExtra("Paper");
        title = findViewById(R.id.paperTitle);
        time = findViewById(R.id.paperTime);
        location = findViewById(R.id.paperLoc);
        paper_abstract = findViewById(R.id.paperAbstract);
        title.setText(paper.getTitle().toString());
        time.setText(paper.getTime().displayTime());
        location.setText(paper.getVenue());
        paper_abstract.setText(paper.getPaper_abstract());
        String[] authorsList = paper.getAuthors();
        for(int i = 0; i < authorsList.length; i++){
            View speakers = getLayoutInflater().inflate(R.layout.inflator_speaker_list, null);
            TextView nameTextView = speakers.findViewById(R.id.name);
            nameTextView.setText(authorsList[i]);
            speakerList.addView(speakers);
        }
        getSupportActionBar().setTitle(paper.getTitle());
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
            intent.putExtra("Source", "skip");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
