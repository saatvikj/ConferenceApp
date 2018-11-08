package com.example.conferenceapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.User;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

public class AttendeeAdapter extends RecyclerView.Adapter<AttendeeAdapter.SpeakerViewHolder> implements SectionTitleProvider {

    private List<User> mSpeakersList;
    private Context mCtx;

    public AttendeeAdapter(List<User> mSpeakersList, Context mCtx) {
        this.mSpeakersList= mSpeakersList;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public SpeakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.inflator_attendee_list, null);
        return new SpeakerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakerViewHolder holder, int position) {
        User user = mSpeakersList.get(position);
        holder.name.setText(user.getName());
        holder.bio.setText(user.getTypeOfUser());

    }

    @Override
    public String getSectionTitle(int position) {
        return mSpeakersList.get(position).getName().substring(0, 1);

    }

    @Override
    public int getItemCount() {
        return mSpeakersList.size();
    }

    class SpeakerViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView bio;

        public SpeakerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            bio = itemView.findViewById(R.id.bio);
        }
    }
}
