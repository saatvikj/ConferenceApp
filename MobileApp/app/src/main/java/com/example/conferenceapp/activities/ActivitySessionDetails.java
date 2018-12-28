package com.example.conferenceapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Event;
import com.example.conferenceapp.models.Session;
import com.example.conferenceapp.utils.EventCSVParser;
import com.example.conferenceapp.utils.ProgramCSVParser;
import com.ramotion.foldingcell.FoldingCell;

import java.io.IOException;
import java.util.ArrayList;

public class ActivitySessionDetails extends AppCompatActivity {

    ArrayList<Event> events;
    ArrayList<Session> sessions;
    FoldingCell cells[];
    FrameLayout content_views[];
    FrameLayout title_views[];

    Session main_session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);
        Toast.makeText(getApplicationContext(), "Click on title to view any additional details if available.", Toast.LENGTH_SHORT).show();

        events = new ArrayList<Event>();
        sessions = new ArrayList<>();
        String id = getIntent().getStringExtra("id");
        try {
            events = EventCSVParser.parseCSV(getApplicationContext(), Integer.parseInt(id));
            sessions = ProgramCSVParser.parseCSV(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cells = new FoldingCell[events.size()];
        content_views = new FrameLayout[events.size()];
        title_views = new FrameLayout[events.size()];
        for (int i=0; i< sessions.size(); i++) {
            if (sessions.get(i).getID() == Integer.parseInt(id)) {
                setTitle(sessions.get(i).getTitle());
                main_session = sessions.get(i);
            }
        }



        LinearLayout session_layout = findViewById(R.id.sessionLayout);
        for(int i = 0; i < events.size(); i++){
            Event event = events.get(i);
            View session = getLayoutInflater().inflate(R.layout.inflator_session,null);
            final FoldingCell fc = (FoldingCell) session.findViewById(R.id.folding_cell);
            cells[i] = fc;
            TextView session_folded_title = session.findViewById(R.id.session_title);
            TextView session_folded_authors = session.findViewById(R.id.session_authors);
            TextView session_folded_venue = session.findViewById(R.id.sessionVenue);
            ImageView session_folded_icon = session.findViewById(R.id.foldedIcon);
            ImageView session_folded_bullet = session.findViewById(R.id.bulletImageView);
            TextView session_unfolded_title = session.findViewById(R.id.session_content_title);
            TextView session_unfolded_venue = session.findViewById(R.id.sessionUnfoldedVenue);
            ImageView session_unfolded_bullet = session.findViewById(R.id.bulletUnfoldedImageView);
            ImageView session_unfolded_icon = session.findViewById(R.id.sessionIcon);

            int bullet_id = getApplicationContext().getResources().getIdentifier(main_session.getBulletDrawable(),"drawable",getApplicationContext().getPackageName());
            session_folded_bullet.setImageResource(bullet_id);

            int icon_id = getApplicationContext().getResources().getIdentifier(main_session.getIconDrawable(),"drawable",getApplicationContext().getPackageName());
            session_folded_icon.setImageResource(icon_id);

            TextView session_unfolded_abstract = session.findViewById(R.id.session_content_abstract);
            TextView session_unfolded_authors = session.findViewById(R.id.session_content_authors);
            final String title = event.getTitle();
            String _abstract = event.get_abstract();
            String[] authors = event.getAuthors();
            String unfolded_authors = "* ";
            String folded_authors = "";

            for(int j = 0; j < authors.length; j++){
                int index = authors[j].indexOf("(");
                String name = "";
                if (index != -1) {
                    name = authors[j].substring(0, index);
                } else {
                    name = authors[j];
                }

                if (j != authors.length-1) {
                    folded_authors = folded_authors.concat(name).concat(", ");
                    unfolded_authors = unfolded_authors.concat(authors[j]).concat(")\n* ");
                } else {
                    folded_authors = folded_authors.concat(name);
                    unfolded_authors = unfolded_authors.concat(authors[j]).concat("\n");
                }
            }
            session_folded_title.setText(title);
            session_folded_venue.setText(event.getRoom());
            session_folded_authors.setText(folded_authors);
            session_unfolded_title.setText(title);
            if (_abstract.equals("") || _abstract.length() == 0) {
                session_unfolded_abstract.setVisibility(View.GONE);
            } else {
                session_unfolded_abstract.setText(_abstract);
            }
            session_unfolded_venue.setText(event.getRoom());
            session_unfolded_authors.setText(unfolded_authors);
            session_unfolded_bullet.setImageResource(bullet_id);
            session_unfolded_icon.setImageResource(icon_id);
            final FrameLayout title_view = (FrameLayout) session.findViewById(R.id.cell_title_view);
            final FrameLayout content_view = (FrameLayout) session.findViewById(R.id.cell_content_view);
            content_views[i] = content_view;
            title_views[i] = title_view;

            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        fold_others(fc);
                        fc.toggle(false);
                    } catch (Exception e) {
                        title_others(content_view, title_view);
                        content_view.setVisibility(View.VISIBLE);
                    }
                }
            });

            session_layout.addView(session);

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


    public void fold_others(FoldingCell fc) {

        for (int i=0; i< cells.length; i++) {
            if (cells[i] != fc) {
                if (cells[i].isUnfolded()) {
                    cells[i].fold(false);
                }
            }
        }

    }

    public void title_others(FrameLayout content, FrameLayout title) {
        for (int i=0; i< content_views.length; i++) {
            if (content_views[i] != content) {
                content_views[i].setVisibility(View.GONE);
                title_views[i].setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivitySessionDetails.this, NavBarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Source", getIntent().getStringExtra("Source"));
        if (!(getIntent().getStringExtra("Source")).equals("skip")) {
            intent.putExtra("email", getIntent().getStringExtra("email"));
        }
        startActivity(intent);
    }

}