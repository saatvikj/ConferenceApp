package com.example.conferenceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
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
        String initial = user.getName().split(" ")[0].substring(0, 1).concat(user.getName().split(" ")[1].substring(0, 1));
        TextDrawable drawable = TextDrawable.builder().buildRound(initial, mCtx.getResources().getColor(R.color.tabtextcolor));
        holder.picture.setImageDrawable(drawable);
        holder.name.setText(user.getName());
        holder.bio.setText(user.getCompany());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String src = fa.getIntent().getStringExtra("Source");
                if (src.equals("skip")) {
                    if (user.getEmail().length() != 0) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        String mailto = "mailto:".concat(user.getEmail());
                        intent.setData(Uri.parse(mailto));
                        mCtx.startActivity(intent);
                    } else {
                        Toast.makeText(mCtx, "No details available.", Toast.LENGTH_LONG).show();
                    }

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
        ImageView picture;

        public SpeakerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            bio = itemView.findViewById(R.id.bio);
            picture = itemView.findViewById(R.id.profilePic);
        }
    }
}
