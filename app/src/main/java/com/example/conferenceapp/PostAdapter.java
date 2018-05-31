package com.example.conferenceapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<FeedPost> mPosts;
    private Context mCtx;

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
        Glide.with(mCtx).load("https://upload.wikimedia.org/wikipedia/commons/0/04/Vivian_Bartley_Green-Armytage_IMS.jpg").into(holder.image);
        holder.numLikes.setText(Integer.toString(feedPost.getLikes()).concat(" likes"));
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.nLikes.getText().toString().equals("Like")) {
                    holder.nLikes.setText(Integer.toString(feedPost.getLikes() + 1));
                    feedPost.likes++;
                    holder.numLikes.setText(Integer.toString(feedPost.getLikes()).concat(" likes"));

                }
                else{
                    holder.nLikes.setText("Like");
                    feedPost.likes--;
                    holder.numLikes.setText(Integer.toString(feedPost.getLikes()).concat(" likes"));
                }
            }
        });

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx,ActivityFeedPost.class);
                mCtx.startActivity(intent);
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
        TextView nLikes;
        ImageView image;
        LinearLayout like;
        LinearLayout comments;
        TextView numLikes;

        public PostViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timeStamp = itemView.findViewById(R.id.timestamp);
            content = itemView.findViewById(R.id.txtStatusMsg);
            image = itemView.findViewById(R.id.feedImage1);
            like = itemView.findViewById(R.id.like);
            nLikes = itemView.findViewById(R.id.nLikes);
            comments = itemView.findViewById(R.id.comment);
            numLikes = itemView.findViewById(R.id.number_likes);
        }
    }
}
