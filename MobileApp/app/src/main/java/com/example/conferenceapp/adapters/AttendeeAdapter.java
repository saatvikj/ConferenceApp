package com.example.conferenceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityUserProfile;
import com.example.conferenceapp.models.User;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

public class AttendeeAdapter extends RecyclerView.Adapter<AttendeeAdapter.SpeakerViewHolder> implements SectionTitleProvider {

    private List<User> mSpeakersList;
    private Context mCtx;
    private FragmentActivity fa;
    private String src;

    public AttendeeAdapter(List<User> mSpeakersList, Context mCtx, FragmentActivity fa, String src) {
        this.mSpeakersList= mSpeakersList;
        this.mCtx = mCtx;
        this.fa = fa;
        this.src = src;
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
        final User user = mSpeakersList.get(position);
        holder.name.setText(user.getName());
        holder.bio.setText(user.getCompany());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (src.equals("skip")) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    String mailto = "mailto:".concat(user.getEmail());
                    intent.setData(Uri.parse(mailto));
                    mCtx.startActivity(intent);
                } else {
                    Intent intent = new Intent(fa, ActivityUserProfile.class);
                    intent.putExtra("email", user.getEmail());
                    mCtx.startActivity(intent);
                }
            }
        });
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
