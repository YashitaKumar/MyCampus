package com.example.mycampus.Adapters;

import android.media.Image;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventsMainAdapter extends RecyclerView.Adapter<EventsMainAdapter.MyViewHolder> {
    List<EventsModel> list;
    Boolean today=false;

    public void setToday(Boolean today) {
        this.today = today;
    }

    public List<EventsModel> getList() {
        return list;
    }

    public EventsMainAdapter() {
    }

    public void setList(List<EventsModel> list) {
        this.list = list;
    }

    public EventsMainAdapter(List<EventsModel> list) {
        this.list = list;
        this.list.removeAll(Collections.singleton(null));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_main_descp,parent,false);
        return new EventsMainAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EventsModel eventsModel = list.get(position);
        holder.name.setText(eventsModel.getEventName());
        holder.descp.setText(eventsModel.getDescp());
        holder.venue.setText(eventsModel.getEventVenue());
        holder.date.setText(eventsModel.getEventDate());
        holder.time.setText(eventsModel.getEventTime());
        Glide.with(holder.itemView.getContext()).load(eventsModel.getEventPic()).into(holder.pic);
        String likes = String.valueOf(eventsModel.getEventLikes());
        holder.likes.setText(likes);

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
                    holder.btnLike.setVisibility(View.INVISIBLE);
                    holder.btnOn.setVisibility(View.VISIBLE);
                    holder.btnFin.setVisibility(View.INVISIBLE);
                    Log.d("Event Status","Live");
                }
                else
                {
                    holder.btnLike.setVisibility(View.INVISIBLE);
                    holder.btnFin.setVisibility(View.VISIBLE);
                    holder.btnOn.setVisibility(View.INVISIBLE);
                    Log.d("Event Status Main","Over");
                }

            }catch (ParseException e) {
                e.printStackTrace();
            }

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

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,descp,date,time,venue,likes;
        Button btnLike,btnOn,btnFin;
        ImageView pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.event_Name);
            descp=itemView.findViewById(R.id.descp2);
            date=itemView.findViewById(R.id.event_date);
            time=itemView.findViewById(R.id.event_time);
            venue=itemView.findViewById(R.id.event_location);
            likes=itemView.findViewById(R.id.event_likes);
            btnLike=itemView.findViewById(R.id.Likke);
            pic=itemView.findViewById(R.id.eventMainPic);
            btnFin=itemView.findViewById(R.id.Finished_Btn);
            btnOn=itemView.findViewById(R.id.Live_Btn);

        }
    }
}
