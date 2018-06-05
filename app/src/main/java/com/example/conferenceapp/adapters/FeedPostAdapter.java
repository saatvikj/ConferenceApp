package com.example.conferenceapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.conferenceapp.models.Comment;
import com.example.conferenceapp.R;

import java.util.List;

public class FeedPostAdapter extends RecyclerView.Adapter<FeedPostAdapter.FeedPostViewHolder> {

    private List<Comment> mComments;
    private Context mCtx;

    public FeedPostAdapter(List<Comment> mComments, Context mCtx) {
        this.mComments = mComments;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public FeedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.inflator_comment, null);
        return new FeedPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedPostViewHolder holder, int position) {
        final Comment comment = mComments.get(position);
        holder.name.setText(comment.getName());
        holder.timeStamp.setText(comment.getTimestamp());
        holder.content.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class FeedPostViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView timeStamp;
        TextView content;

        public FeedPostViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timeStamp = itemView.findViewById(R.id.timestamp);
            content = itemView.findViewById(R.id.txtStatusMsg);
        }
    }
}
