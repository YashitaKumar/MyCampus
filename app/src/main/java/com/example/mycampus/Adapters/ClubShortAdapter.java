package com.example.mycampus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycampus.AllClubRelatedModels.ClubModel;
import com.example.mycampus.ClubMainActivity;
import com.example.mycampus.Models.DhabbaItemModel;
import com.example.mycampus.R;

import java.util.Collections;
import java.util.List;

public class ClubShortAdapter extends RecyclerView.Adapter<ClubShortAdapter.MyViewHolder> {
    private List<ClubModel> clubModelList;
    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ClubModel> getClubModelList() {
        return clubModelList;
    }

    public void setClubModelList(List<ClubModel> clubModelList) {
        this.clubModelList = clubModelList;
    }

    public ClubShortAdapter() {
    }

    public ClubShortAdapter(List<ClubModel> clubModelList) {
        this.clubModelList = clubModelList;
        this.clubModelList.removeAll(Collections.singleton(null));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_short,parent,false);
        return new ClubShortAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClubModel clubModel = clubModelList.get(position);
        Glide.with(holder.itemView.getContext()).load(clubModel.getClubPic()).into(holder.bg);
        holder.clubName.setText(clubModel.getName());
        holder.shortDesc.setText(clubModel.getShortdescp());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClubMainActivity.class);
                intent.putExtra("clubName",clubModel.getName().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(clubModelList!=null)
        {
            return clubModelList.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView bg,more;
        TextView clubName,shortDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bg = itemView.findViewById(R.id.clubPic);
            more = itemView.findViewById(R.id.moreInfo);
            clubName = itemView.findViewById(R.id.clubName);
            shortDesc= itemView.findViewById(R.id.shortdescp);



        }
    }
}
