package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.conferenceapp.R;

public class ActivityFoodGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_guide);
        getSupportActionBar().setTitle("Food Guide");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guide_home) {
            Intent intent = new Intent(ActivityFoodGuide.this, NavBarActivity.class);
            intent.putExtra("Source", "skip");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
