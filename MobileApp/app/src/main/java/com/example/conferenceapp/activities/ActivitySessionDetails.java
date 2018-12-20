package com.example.conferenceapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Event;
import com.example.conferenceapp.utils.EventCSVParser;
import com.ramotion.foldingcell.FoldingCell;

import java.io.IOException;
import java.util.ArrayList;

public class ActivitySessionDetails extends AppCompatActivity {

    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        events = new ArrayList<Event>();

        String id = getIntent().getStringExtra("id");
        try {
            events = EventCSVParser.parseCSV(getApplicationContext(), Integer.parseInt(id));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout session_layout = findViewById(R.id.sessionLayout);
        for(int i = 0; i < events.size(); i++){
            Event event = events.get(i);
            final FoldingCell fc = (FoldingCell) getLayoutInflater().inflate(R.layout.inflator_session, null);
            TextView session_folded_title = findViewById(R.id.session_title);
            TextView session_folded_authors = findViewById(R.id.session_authors);
            TextView session_unfolded_title = findViewById(R.id.session_content_title);
            TextView session_unfolded_abstract = findViewById(R.id.session_content_abstract);
            TextView session_unfolded_authors = findViewById(R.id.session_content_authors);
            String title = event.getTitle();
            String _abstract = event.get_abstract();
            String[] authors = event.getAuthors();
            String unfolded_authors = String.join("\n", authors);
            String folded_authors;
            for(int j = 0; j < authors.length; j++){
                int index = authors[j].indexOf("(");
                String name = authors[j].substring(0, index);
                ma
            }

            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                }
            });

            session_layout.addView(fc);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guide_home) {
            Intent intent = new Intent(ActivitySessionDetails.this, NavBarActivity.class);
            intent.putExtra("Source", getIntent().getStringExtra("Source"));
            if (!(getIntent().getStringExtra("Source")).equals("skip")) {
                intent.putExtra("email", getIntent().getStringExtra("email"));
            }
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
