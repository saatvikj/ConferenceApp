package com.example.conferenceapp.activities;

import com.example.conferenceapp.models.Event;
import com.example.conferenceapp.utils.*;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.conferenceapp.R;

import java.io.IOException;
import java.util.ArrayList;

import mehdi.sakout.aboutpage.AboutPage;

public class ActivityKeynote extends AppCompatActivity {

    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keynote);

        LinearLayout content = findViewById(R.id.keynote_content);
        ImageView imageView = findViewById(R.id.keynote_image);
        String id = getIntent().getStringExtra("id");
        try {
            events = EventCSVParser.parseCSV(getApplicationContext(), Integer.parseInt(id));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Event e = events.get(0);
        if(e.getAuthors()[0].equals("Aruna Roy")){
            setTitle("Aruna Roy");
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract());

            content.addView(page.create());
            imageView.setImageResource(R.drawable.aruna);
        }

        else if(e.getAuthors()[0].equals("Rahul Panicker")){
            setTitle("Rahul Panicker");
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract());

            content.addView(page.create());
            imageView.setImageResource(R.drawable.rahul);
        }

        else if(e.getAuthors()[0].equals("Smitha Radhakrishnan")){
            setTitle("Smitha Radhakrishnan");
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract());

            content.addView(page.create());
            imageView.setImageResource(R.drawable.smitha);
        }

        else if(e.getAuthors()[0].equals("Murali Shanmugavelan")){
            setTitle("Murali Shanmugavelan");
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract());

            content.addView(page.create());
            imageView.setImageResource(R.drawable.murali);
        } else if (e.getAuthors()[0].equals("") || e.getAuthors()[0].length() == 0) {
            setTitle(e.getTitle());
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setImage(R.drawable.logo)
                    .setDescription(e.get_abstract());

            content.addView(page.create());
        }

        else {
            setTitle("Michael Mazgaonkar");
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract());

            content.addView(page.create());
            imageView.setImageResource(R.drawable.michael);
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
            Intent intent = new Intent(ActivityKeynote.this, NavBarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Source", getIntent().getStringExtra("Source"));
            if (!(getIntent().getStringExtra("Source")).equals("skip")) {
                intent.putExtra("email", getIntent().getStringExtra("email"));
            }
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityKeynote.this, NavBarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Source", getIntent().getStringExtra("Source"));
        if (!(getIntent().getStringExtra("Source")).equals("skip")) {
            intent.putExtra("email", getIntent().getStringExtra("email"));
        }
        startActivity(intent);
    }
}
