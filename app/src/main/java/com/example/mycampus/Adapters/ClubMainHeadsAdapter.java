package com.example.mycampus.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycampus.AllClubRelatedModels.ClubModel;
import com.example.mycampus.AllClubRelatedModels.HeadsModels;
import com.example.mycampus.R;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubMainHeadsAdapter extends RecyclerView.Adapter<ClubMainHeadsAdapter.MyViewHolder>{
    private List<HeadsModels> headsModelsList;

    public List<HeadsModels> getHeadsModelsList() {
        return headsModelsList;
    }

    public void setHeadsModelsList(List<HeadsModels> headsModelsList) {
        this.headsModelsList = headsModelsList;
    }

    public ClubMainHeadsAdapter() {
    }

    public ClubMainHeadsAdapter(List<HeadsModels> headsModelsList) {
        this.headsModelsList = headsModelsList;
        this.headsModelsList.removeAll(Collections.singleton(null));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heads_layout,parent,false);
        return new ClubMainHeadsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HeadsModels headsModels = headsModelsList.get(position);
        holder.name.setText(headsModels.getHeadName());
        holder.desg.setText(headsModels.getHeadDesg());
        Glide.with(holder.itemView.getContext()).load(headsModels.getHeadPic()).into(holder.headPic);

    }

    @Override
    public int getItemCount() {
        if(headsModelsList!=null)
        {
            return headsModelsList.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,desg;
        CircleImageView headPic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.headName);
            desg=itemView.findViewById(R.id.headDesg);
            headPic=itemView.findViewById(R.id.headPic);

        }
    }
}
