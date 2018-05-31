package com.example.conferenceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = inflater.inflate(R.layout.layout_partners, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
        final FeedPost feedPost = mPosts.get(position);
        holder.name.setText(feedPost.getName());
        holder.timeStamp.setText(feedPost.getTime());
        holder.content.setText(feedPost.getContent());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.nLikes.setText(feedPost.getLikes()+1);
                feedPost.likes++;
            }
        });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,"Comment will be made",Toast.LENGTH_LONG).show();
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

        public PostViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timeStamp = itemView.findViewById(R.id.timestamp);
            content = itemView.findViewById(R.id.txtStatusMsg);
            image = itemView.findViewById(R.id.feedImage1);
            like = itemView.findViewById(R.id.like);
            nLikes = itemView.findViewById(R.id.nLikes);
            comments = itemView.findViewById(R.id.comment);
        }
    }
}
