package com.example.mycampus.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.R;

import java.util.Collections;
import java.util.List;

public class EventShortAdapter extends RecyclerView.Adapter<EventShortAdapter.MyViewHolder> {
    List<EventsModel> list;

    public List<EventsModel> getList() {
        return list;
    }

    public void setList(List<EventsModel> list) {
        this.list = list;
    }

    public EventShortAdapter(List<EventsModel> list) {
        this.list = list;
        this.list.removeAll(Collections.singleton(null));
    }

    public EventShortAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item,parent,false);
        return new EventShortAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EventsModel eventsModel = list.get(position);
        holder.name.setText(eventsModel.getEventName());
        holder.club.setText(eventsModel.getEventClub());
        holder.location.setText(eventsModel.getEventVenue());
        holder.date.setText(eventsModel.getEventDate());
        holder.time.setText(eventsModel.getEventTime());
        Glide.with(holder.itemView.getContext()).load(eventsModel.getEventPic()).into(holder.pic);
        String likes = String.valueOf(eventsModel.getEventLikes());
        holder.likes.setText(likes);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int curr = eventsModel.getEventLikes();
//                curr+=1;

            }
        });

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

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,club,likes,location,date,time;
        ImageView pic;
        Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.eventName);
            club=itemView.findViewById(R.id.eventClub);
            likes=itemView.findViewById(R.id.eventLikes);
            location=itemView.findViewById(R.id.eventVenu);
            date=itemView.findViewById(R.id.eventDate);
            time=itemView.findViewById(R.id.eventTime);
            pic=itemView.findViewById(R.id.eventPic);
            btn=itemView.findViewById(R.id.likeBtn);

        }
    }
}
