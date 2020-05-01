package com.example.conferenceapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.MainApplication;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.UserCSVParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityPaperDetails extends AppCompatActivity implements Serializable{

    TextView title;
    TextView time;
    TextView location;
    TextView paper_abstract;
    public String src;
    ArrayList<User> users;

    @Override
    protected void onResume() {
        src = ((MainApplication) getApplication()).getType();
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        src = ((MainApplication) getApplication()).getType();
        try {
            users = UserCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }

        LinearLayout speakerList = findViewById(R.id.speakerListLayout);
        Intent intent = getIntent();
        Paper paper = (Paper) intent.getSerializableExtra("Paper");
        title = findViewById(R.id.paperTitle);
        time = findViewById(R.id.paperTime);
        location = findViewById(R.id.paperLoc);
        paper_abstract = findViewById(R.id.paperAbstract);
        title.setText(paper.getTitle());
        time.setText(paper.getTime().displayTime());
        location.setText(paper.getVenue());
        paper_abstract.setText(paper.getPaper_abstract());
        String[] authorsList = paper.getAuthors();
        final User[] authors = new User[authorsList.length];
        int j=0;
        for (int i=0; i<users.size(); i++) {
            if (Arrays.asList(authorsList).contains(users.get(i).getName())) {
                authors[j] = users.get(i);
                j++;
            }
        }

        for(int i = 0; i < authors.length; i++){
            final User user = authors[i];
            View speakers = getLayoutInflater().inflate(R.layout.inflator_attendee_list, null);
            TextView nameTextView = speakers.findViewById(R.id.name);
            TextView emailTextView = speakers.findViewById(R.id.bio);
            nameTextView.setText(authors[i].getName());
            emailTextView.setText(authors[i].getCompany());
            speakerList.addView(speakers);
            speakers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (src.equals("skip")) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        String mailto = "mailto:".concat(user.getEmail());
                        intent.setData(Uri.parse(mailto));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ActivityPaperDetails.this, ActivityUserProfile.class);
                        intent.putExtra("email",user.getEmail());
                        startActivity(intent);
                    }
                }
            });
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
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
