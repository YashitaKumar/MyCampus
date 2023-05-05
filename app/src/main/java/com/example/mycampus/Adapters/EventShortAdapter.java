package com.example.mycampus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.EventsMainActivity;
import com.example.mycampus.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventShortAdapter extends RecyclerView.Adapter<EventShortAdapter.MyViewHolder> {
    List<EventsModel> list;
    Boolean today = false;
    Boolean past = false;
    Context context ;

    public void setPast(Boolean past) {
        this.past = past;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setToday(Boolean today) {
        this.today = today;
    }

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

        //Main Description page
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsMainActivity.class);
                String eventN = eventsModel.getEventName();
                intent.putExtra("event",eventN);
                intent.putExtra("today",today);
                intent.putExtra("past",past);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



        //Button Functionality
        if(today)
        {
            Log.d("Working","This loop working");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            try {
                Date date = new Date();
                Date eventdate = formatter.parse((eventsModel.getEventDate()+" "+eventsModel.getEventTime()));
                long curr = date.getTime();
                long events = eventdate.getTime();
                long oneHourInMillis = TimeUnit.HOURS.toMillis(2);
                long end = events+oneHourInMillis;
                String val1,val2,val3;
                val1=String.valueOf(curr);
                val2=String.valueOf(events);
                val3=String.valueOf(end);
                long currT = Long.parseLong(val1);
                long eventT=Long.parseLong(val2);
                long endT = Long.parseLong(val3);
                Log.d("Event Time",val2);
                Log.d("Current Time", val1);
                Log.d("Ending Time",val3);
                if((currT>=eventT)&&(currT<=endT)){
                    holder.btn.setVisibility(View.INVISIBLE);
                    holder.live.setVisibility(View.VISIBLE);
                    holder.finish.setVisibility(View.INVISIBLE);
                    Log.d("Event Status","Live");
                }
                else if(currT>=endT)
                {
                    holder.live.setVisibility(View.INVISIBLE);
                    holder.btn.setVisibility(View.INVISIBLE);
                    holder.finish.setVisibility(View.VISIBLE);
                    Log.d("Event Status","Over");
                }

            }catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(past)
        {
            holder.live.setVisibility(View.INVISIBLE);
            holder.btn.setVisibility(View.INVISIBLE);
            holder.finish.setVisibility(View.VISIBLE);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curr = eventsModel.getEventLikes();
                curr+=1;
                int posi = holder.getAdapterPosition()+1;
                String pos = String.valueOf(posi);
                String likes = String.valueOf(curr);
                holder.likes.setText(likes);
                FirebaseDatabase.getInstance().getReference("/Events/"+pos+"/eventLikes").setValue(curr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(holder.itemView.getContext(),"See you at the event",Toast.LENGTH_SHORT ).show();
                        Log.d("Button","U clicked it");

                    }
                });

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
        Button btn,finish,live;

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
            live = itemView.findViewById(R.id.LiveBtn);
            finish = itemView.findViewById(R.id.FinishedBtn);

        }
    }
}
