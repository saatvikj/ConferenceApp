package com.example.conferenceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityHashtags;
import com.example.conferenceapp.models.FeedPost;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<FeedPost> mPosts;
    private Context mCtx;
    private String email;
    private String conference_id;
    private DatabaseReference mDatabase;
    private LinearLayout no_posts;

    public PostAdapter(List<FeedPost> mPosts, Context mCtx, String email, String conference_id, LinearLayout no_posts) {
        this.mPosts = mPosts;
        this.mCtx = mCtx;
        this.email = email;
        this.conference_id = conference_id;
        this.no_posts = no_posts;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.feed_item, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        final FeedPost feedPost = mPosts.get(position);
        holder.name.setText(feedPost.getName());
        holder.timeStamp.setText(DateUtils.getRelativeTimeSpanString(feedPost.getTime()));
        holder.content.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_URL);

        holder.content.setHashtagModeColor(ContextCompat.getColor(mCtx, R.color.urlColor));
        holder.content.setUrlModeColor(ContextCompat.getColor(mCtx, R.color.urlColor));

        holder.content.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                if (autoLinkMode.equals(AutoLinkMode.MODE_URL)) {
                    if (!matchedText.startsWith("http://") && !matchedText.startsWith("https://"))
                        matchedText = "http://" + matchedText;

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(matchedText));
                    mCtx.startActivity(browserIntent);
                } else {
                    Intent intent = new Intent(mCtx, ActivityHashtags.class);
                    intent.putExtra("hashtag", matchedText);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(intent);
                }
            }
        });

        holder.content.setAutoLinkText(feedPost.getContent());

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, holder.content.getText().toString());
                sendIntent.setType("text/plain");
                mCtx.startActivity(sendIntent);
            }
        });

        if (feedPost.getEmail().equals(email)) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPosts.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mPosts.size());

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot d: dataSnapshot.child(conference_id).child("Posts").getChildren()) {
                                FeedPost post = d.getValue(FeedPost.class);
                                if (post.equals(feedPost)) {
                                    d.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if (mPosts.size() == 0) {
                        no_posts.setVisibility(View.VISIBLE);
                    }
                }
            });


        } else {
            holder.delete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView timeStamp;
        ImageView share;
        ImageView delete;
        AutoLinkTextView content;


        public PostViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timeStamp = itemView.findViewById(R.id.timestamp);
            content = (AutoLinkTextView) itemView.findViewById(R.id.txtStatusMsg);
            share = itemView.findViewById(R.id.share_post);
            delete = itemView.findViewById(R.id.delete_post);
        }
    }
}