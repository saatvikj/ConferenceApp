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
import com.example.conferenceapp.activities.NavBarActivity;
import com.example.conferenceapp.models.User;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.List;

public class AttendeeAdapter extends RecyclerView.Adapter<AttendeeAdapter.SpeakerViewHolder> implements SectionTitleProvider {

    private List<User> mSpeakersList;
    private Context mCtx;
    private FragmentActivity fa;

    public AttendeeAdapter(List<User> mSpeakersList, Context mCtx, FragmentActivity fa) {
        this.mSpeakersList= mSpeakersList;
        this.mCtx = mCtx;
        this.fa = fa;
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
        holder.bio.setText(user.getTypeOfUser());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String src = fa.getIntent().getStringExtra("Source");
                if (src.equals("skip")) {

                } else {
                    Intent intent = new Intent(fa, ActivityUserProfile.class);
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("Source","paid");
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
