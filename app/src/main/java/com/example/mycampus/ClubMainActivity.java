package com.example.mycampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.mycampus.Adapters.ClubMainAdapter;
import com.example.mycampus.Adapters.DhabbaAdapter;
import com.example.mycampus.AllClubRelatedModels.ClubModel;
import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.AllClubRelatedModels.HeadsModels;
import com.example.mycampus.Models.DhabbaItemModel;
import com.example.mycampus.Models.DhabbaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClubMainActivity extends AppCompatActivity {

    String club;
    List<ClubModel> list;
    RecyclerView recyclerView;
    List<EventsModel> eventsModelList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_main);

        club=getIntent().getStringExtra("clubName");

        recyclerView = findViewById(R.id.clubMainDescp);

        //Events List
        eventsModelList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("/Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    String eventClub= data.child("eventClub").getValue().toString();
                    if(eventClub.equalsIgnoreCase(club))
                    {
                        String eventDate= data.child("eventDate").getValue().toString();
                        String Likes=data.child("eventLikes").getValue().toString();
                        int eventLikes = Integer.parseInt(Likes);
                        String eventName= data.child("eventName").getValue().toString();
                        String eventPic=data.child("eventPic").getValue().toString();
                        String eventTime = data.child("eventTime").getValue().toString();
                        String eventVenue = data.child("eventVenue").getValue().toString();
                        EventsModel eventsModel = new EventsModel(eventName,eventClub,eventTime,eventDate,eventPic,eventVenue,eventLikes);
                        //Checking the date
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date event = formatter.parse(eventDate);
                            Date date = new Date();
                            if(event.before(date))
                            {
                                eventsModelList.add(eventsModel);
                            }
                        }catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ClubMainAdapter clubMainAdapter = new ClubMainAdapter();
        recyclerView.setAdapter(clubMainAdapter);


        FirebaseDatabase.getInstance().getReference("/Clubs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    GenericTypeIndicator<ArrayList<HeadsModels>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<HeadsModels>>(){};
                    String clubName= data.child("name").getValue().toString();
                    String clubDescp=data.child("descp").getValue().toString();
                    String Pic = data.child("clubPic").getValue().toString();
                    if(club.equalsIgnoreCase(clubName)) {
                        ClubModel clubModel = new ClubModel(clubName, clubDescp, Pic, data.child("clubHeads").getValue(genericTypeIndicator));
                        clubModel.setEventsModelList(eventsModelList);
                        list.add(clubModel);
                    }
                }
                clubMainAdapter.setList(list);
                clubMainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}