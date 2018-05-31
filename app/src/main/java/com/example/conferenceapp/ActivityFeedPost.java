package com.example.conferenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ActivityFeedPost extends AppCompatActivity {

    FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_post);
        RecyclerView recyclerView = findViewById(R.id.mCommentsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fab = findViewById(R.id.fab);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Saatvik Jain","14h ago","Eam ceteros abhorreant at. Ne vis sanctus percipit, at vix autem evertitur suscipiantur. Vel et alterum dignissim voluptaria, graecis molestie erroribus ut his."));
        comments.add(new Comment("Saatvik Jain","14h ago","Eam ceteros abhorreant at. Ne vis sanctus percipit, at vix autem evertitur suscipiantur. Vel et alterum dignissim voluptaria, graecis molestie erroribus ut his."));
        comments.add(new Comment("Meghna Gupta","4h ago","His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        comments.add(new Comment("Meghna Gupta","4h ago","His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        comments.add(new Comment("Saatvik Jain","14h ago","Eam ceteros abhorreant at. Ne vis sanctus percipit, at vix autem evertitur suscipiantur. Vel et alterum dignissim voluptaria, graecis molestie erroribus ut his."));
        comments.add(new Comment("Meghna Gupta","4h ago","His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        comments.add(new Comment("Meghna Gupta","4h ago","His eu wisi verterem lobortis. No veri deterruisset pro, ei choro fastidii est. Nullam splendide his cu, his ludus consetetur ne. Saepe feugiat id ius."));
        FeedPostAdapter feedPostAdapter = new FeedPostAdapter(comments, getApplicationContext());
        recyclerView.setAdapter(feedPostAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        }
}
