package com.example.mycampus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycampus.AllClubRelatedModels.ClubModel;
import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.Models.DhabbaModel;
import com.example.mycampus.R;

import java.util.List;

public class ClubMainAdapter extends RecyclerView.Adapter<ClubMainAdapter.MyViewHolder> {
    private List<ClubModel> list;
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ClubModel> getList() {
        return list;
    }

    public void setList(List<ClubModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_main_descp,parent,false);
        return new ClubMainAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClubModel clubModel = list.get(position);
        holder.clubName.setText(clubModel.getName());
        holder.descp.setText(clubModel.getDescp());
        Glide.with(holder.itemView.getContext()).load(clubModel.getClubPic()).into(holder.clubPic);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ClubMainHeadsAdapter clubMainHeadsAdapter= new ClubMainHeadsAdapter(clubModel.getHeadsModelsList());
        holder.recyclerView.setAdapter(clubMainHeadsAdapter);
        clubMainHeadsAdapter.notifyDataSetChanged();

        List<EventsModel> pastEvents = clubModel.getEventsModelList();
        if(!pastEvents.isEmpty()) {
            holder.past.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            EventShortAdapter eventShortAdapter = new EventShortAdapter();
            eventShortAdapter.setList(pastEvents);
            eventShortAdapter.setPast(true);
            eventShortAdapter.setToday(false);
            eventShortAdapter.setContext(context);
            holder.past.setAdapter(eventShortAdapter);
            eventShortAdapter.notifyDataSetChanged();
        }



    }

    @Override
    public int getItemCount() {
        if(list!=null)
        {
            return list.size();
        }
        else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clubName,descp;
        ImageView clubPic;
        RecyclerView recyclerView, past;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            clubName = itemView.findViewById(R.id.clubName);
            descp = itemView.findViewById(R.id.descp);
            recyclerView = itemView.findViewById(R.id.heads);
            clubPic = itemView.findViewById(R.id.clubMainPic);
            past = itemView.findViewById(R.id.past);
        }
    }
}
