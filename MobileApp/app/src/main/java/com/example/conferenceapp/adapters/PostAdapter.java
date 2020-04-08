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
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityHashtags;
import com.example.conferenceapp.models.FeedPost;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<FeedPost> mPosts;
    private Context mCtx;
    private String email;

    public PostAdapter(List<FeedPost> mPosts, Context mCtx, String email) {
        this.mPosts = mPosts;
        this.mCtx = mCtx;
        this.email = email;
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
        holder.timeStamp.setText(DateUtils.getRelativeTimeSpanString(feedPost.getTime()));
        holder.content.addAutoLinkMode(AutoLinkMode.MODE_HASHTAG, AutoLinkMode.MODE_URL);

        holder.content.setHashtagModeColor(ContextCompat.getColor(mCtx, R.color.urlColor));
        holder.content.setUrlModeColor(ContextCompat.getColor(mCtx, R.color.urlColor));

        holder.content.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @Override
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String matchedText) {
                if (autoLinkMode.equals(AutoLinkMode.MODE_URL)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(matchedText));
                    mCtx.startActivity(browserIntent);
                } else {
                    Intent intent = new Intent(mCtx, ActivityHashtags.class);
                    intent.putExtra("hashtag", matchedText);
                    intent.putExtra("email", email);
                    mCtx.startActivity(intent);
                }
            }
        });

        holder.content.setAutoLinkText(feedPost.getContent());

        //TODO: Add share on click!
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView timeStamp;
        AutoLinkTextView content;


        public PostViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timeStamp = itemView.findViewById(R.id.timestamp);
            content = (AutoLinkTextView) itemView.findViewById(R.id.txtStatusMsg);
        }
    }
}