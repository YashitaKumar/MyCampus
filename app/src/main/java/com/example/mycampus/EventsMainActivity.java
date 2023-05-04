package com.example.mycampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mycampus.Adapters.EventShortAdapter;
import com.example.mycampus.Adapters.EventsMainAdapter;
import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsMainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<EventsModel> list;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_main);
        Boolean todayVal;

        recyclerView = findViewById(R.id.eventMainDescp);
        name = getIntent().getStringExtra("event");
        todayVal=getIntent().getBooleanExtra("today",false);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventsMainAdapter eventsMainAdapter = new EventsMainAdapter();
        eventsMainAdapter.setToday(todayVal);
        recyclerView.setAdapter(eventsMainAdapter);

        FirebaseDatabase.getInstance().getReference("/Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    String eventName = data.child("eventName").getValue().toString();
                    if(eventName.equalsIgnoreCase(name))
                    {
                        String eventDescp = data.child("descp").getValue().toString();
                        String eventPic=data.child("eventPic").getValue().toString();
                        String eventDate=data.child("eventDate").getValue().toString();
                        String eventTime=data.child("eventTime").getValue().toString();
                        String eventVenue=data.child("eventVenue").getValue().toString();
                        String Likes=data.child("eventLikes").getValue().toString();
                        int eventLikes = Integer.parseInt(Likes);
                        EventsModel eventsModel = new EventsModel(eventName,"",eventTime,eventDate,eventPic,eventVenue,eventDescp,eventLikes);
                        list.add(eventsModel);
                    }
                }
                eventsMainAdapter.setList(list);
                eventsMainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}