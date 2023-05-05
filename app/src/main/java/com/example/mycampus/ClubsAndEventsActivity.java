package com.example.mycampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.databinding.ActivityClubsAndEventsBinding;
import com.example.mycampus.databinding.ActivityUploadingFileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ClubsAndEventsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView clubs,today,upcoming,selected,user;
    FrameLayout frameLayout;
    MainActivity.SwipeListener swipeListener;
    FloatingActionButton createEvent;
    String id;
    AlertDialog dialog;
    String eventClub;
    long pos=0;
    String userName;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_and_events);



        //Views on page
        clubs=findViewById(R.id.clubs);
        today=findViewById(R.id.today);
        upcoming=findViewById(R.id.upcoming);
        selected=findViewById(R.id.select);
        frameLayout=findViewById(R.id.fragment_container);
        createEvent = findViewById(R.id.CreateEvent);
        user=findViewById(R.id.userName);

        //From intent
        id=getIntent().getStringExtra("id");
        user.setText(getIntent().getStringExtra("name"));



        //Event Creation
        //User authentication
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("ClubHeads").hasChild(id))
                {
                    createEvent.setVisibility(View.VISIBLE);
                    eventClub = snapshot.child("ClubHeads").child(id).child("club").getValue().toString();
                    pos=snapshot.child("Events").getChildrenCount()+1;

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Fragment displays
        replaceFrgament(new ClubsFragment());

        //onclick listeners
        today.setOnClickListener(this);
        upcoming.setOnClickListener(this);
        clubs.setOnClickListener(this);
        createEvent.setOnClickListener(this);


    }

    public void replaceFrgament(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).setReorderingAllowed(true).addToBackStack("name").commit();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.clubs:selected.animate().x(0).setDuration(100);
                            replaceFrgament(new ClubsFragment());
                            break;
            case R.id.today: int size =clubs.getWidth();
                            selected.animate().x(size).setDuration(100);
                            replaceFrgament(new TodayEventsFragment());
                            break;
            case R.id.upcoming: int size2 =clubs.getWidth();
                                int size1=today.getWidth();
                                selected.animate().x((size2+size1)).setDuration(100);
                                replaceFrgament(new UpcomingEventsFragment());
                                break;
            case R.id.CreateEvent: Intent intent = new Intent(ClubsAndEventsActivity.this,CreateEventActivity.class);
                                    intent.putExtra("id",id);
                                    intent.putExtra("club",eventClub);
                                    intent.putExtra("posistion",pos);
                                    intent.putExtra("name",userName);
                                    startActivity(intent);
                                    break;
        }
    }
}