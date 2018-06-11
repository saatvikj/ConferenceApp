package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.models.Comment;
import com.example.conferenceapp.models.FeedPost;
import com.example.conferenceapp.adapters.FeedPostAdapter;
import com.example.conferenceapp.adapters.PostAdapter;
import com.example.conferenceapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityFeedPost extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_post);
        final FeedPost feedPost = PostAdapter.selectedPost;
        View post = findViewById(R.id.post);
        TextView name = post.findViewById(R.id.name);
        name.setText(feedPost.getName());
        TextView timeStamp = post.findViewById(R.id.timestamp);
        timeStamp.setText(feedPost.getTime());
        TextView content = post.findViewById(R.id.txtStatusMsg);
        content.setText(feedPost.getContent());
        final TextView nLikes = post.findViewById(R.id.number_likes);
        nLikes.setText(Integer.toString(feedPost.getLikes()).concat(" likes"));
        final TextView numLikes = post.findViewById(R.id.nLikes);
        LinearLayout like = post.findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numLikes.getText().toString().equals("Like")) {
                    numLikes.setText(Integer.toString(feedPost.getLikes() + 1));
                    feedPost.likes++;
                    nLikes.setText(Integer.toString(feedPost.getLikes()).concat(" likes"));
                } else {
                    numLikes.setText("Like");
                    feedPost.likes--;
                    nLikes.setText(Integer.toString(feedPost.getLikes()).concat(" likes"));
                }
            }
        });
        RecyclerView recyclerView = findViewById(R.id.mCommentsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fab = findViewById(R.id.fab);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Saatvik Jain", "14h ago", "Eam ceteros abhorreant at. Ne vis sanctus percipit, at vix autem evertitur suscipiantur. Vel et alterum dignissim voluptaria, graecis molestie erroribus ut his."));
        comments.add(new Comment("Saatvik Jain", "14h ago", "Eam ceteros abhorreant at. Ne vis sanctus percipit, at vix autem evertitur suscipiantur. Vel et alterum dignissim voluptaria, graecis molestie erroribus ut his."));
        comments.add(new Comment("Meghna Gupta", "4h ago", "His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        comments.add(new Comment("Meghna Gupta", "4h ago", "His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        comments.add(new Comment("Saatvik Jain", "14h ago", "Eam ceteros abhorreant at. Ne vis sanctus percipit, at vix autem evertitur suscipiantur. Vel et alterum dignissim voluptaria, graecis molestie erroribus ut his."));
        comments.add(new Comment("Meghna Gupta", "4h ago", "His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        comments.add(new Comment("Meghna Gupta", "4h ago", "His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        FeedPostAdapter feedPostAdapter = new FeedPostAdapter(comments, getApplicationContext());
        recyclerView.setAdapter(feedPostAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guide_home) {
            Intent intent = new Intent(ActivityFeedPost.this, NavBarActivity.class);
            intent.putExtra("Source", "normal");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
