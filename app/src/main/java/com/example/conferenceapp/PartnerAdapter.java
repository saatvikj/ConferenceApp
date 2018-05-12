package com.example.conferenceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.PartnerViewHolder> {

    private List<Partner> mPartnersList;
    private Context mCtx;

    public PartnerAdapter(List<Partner> mPartnersList, Context mCtx) {
        this.mPartnersList = mPartnersList;
        this.mCtx = mCtx;
    }


    @NonNull
    @Override
    public PartnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_partners, null);
        return new PartnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartnerViewHolder holder, int position) {
        Partner partner = mPartnersList.get(position);
        holder.name.setText(partner.getSponsorName());
        holder.type.setText(partner.getSponsorType());
        holder.photo.setImageDrawable(mCtx.getResources().getDrawable(partner.getImageID()));
    }

    @Override
    public int getItemCount() {
        return mPartnersList.size();
    }

    class PartnerViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView type;
        ImageView photo;

        public PartnerViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.partnerName);
            type = (TextView) itemView.findViewById(R.id.partnerType);
            photo = (ImageView)itemView.findViewById(R.id.partnerImage);

        }
    }
}
