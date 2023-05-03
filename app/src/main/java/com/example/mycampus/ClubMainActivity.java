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
import com.example.mycampus.AllClubRelatedModels.HeadsModels;
import com.example.mycampus.Models.DhabbaItemModel;
import com.example.mycampus.Models.DhabbaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClubMainActivity extends AppCompatActivity {

    String club;
    List<ClubModel> list;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_main);

        club=getIntent().getStringExtra("clubName");

        recyclerView = findViewById(R.id.clubMainDescp);

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