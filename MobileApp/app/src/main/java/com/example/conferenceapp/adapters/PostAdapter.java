package com.example.conferenceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.conferenceapp.models.FeedPost;
import com.example.conferenceapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<FeedPost> mPosts;
    private Context mCtx;
    public static FeedPost selectedPost;

    public PostAdapter(List<FeedPost> mPosts, Context mCtx) {
        this.mPosts = mPosts;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.feed_item, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
        final FeedPost feedPost = mPosts.get(position);
        holder.name.setText(feedPost.getName());
        holder.timeStamp.setText(feedPost.getTime());
        holder.content.setText(feedPost.getContent());
        String initials = feedPost.getName().substring(0,1);
        TextDrawable textDrawable = TextDrawable.builder().buildRound(initials, Color.GRAY);
        holder.profilePic.setImageDrawable(textDrawable);
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,feedPost.content);
                mCtx.startActivity(Intent.createChooser(intent,"Select Application"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView timeStamp;
        TextView content;
        ImageView profilePic;
        ImageView share;

        public PostViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timeStamp = itemView.findViewById(R.id.timestamp);
            content = itemView.findViewById(R.id.txtStatusMsg);
            profilePic = itemView.findViewById(R.id.profilePic);
            share = itemView.findViewById(R.id.sharePost);
        }
    }
}
