package com.example.conferenceapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.conferenceapp.models.Partner;
import com.example.conferenceapp.R;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder> implements SectionTitleProvider {

    private List<String> mSpeakersList;
    private Context mCtx;

    public SpeakerAdapter(List<String> mSpeakersList, Context mCtx) {
        this.mSpeakersList= mSpeakersList;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public SpeakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.inflator_speaker_list, null);
        return new SpeakerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakerViewHolder holder, int position) {
        String speaker = mSpeakersList.get(position);
        holder.name.setText(speaker);

    }

    @Override
    public String getSectionTitle(int position) {
        return mSpeakersList.get(position).substring(0, 1);

    }

    @Override
    public int getItemCount() {
        return mSpeakersList.size();
    }

    class SpeakerViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public SpeakerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

        }
    }
}
