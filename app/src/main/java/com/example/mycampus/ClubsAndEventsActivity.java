package com.example.mycampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ClubsAndEventsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView clubs,today,upcoming,selected;
    FrameLayout frameLayout;
    MainActivity.SwipeListener swipeListener;


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

        //Fragment displays
        replaceFrgament(new ClubsFragment());

        //onclick listeners
        today.setOnClickListener(this);
        upcoming.setOnClickListener(this);
        clubs.setOnClickListener(this);


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
            case R.id.today:
            case R.id.upcoming:
        }
    }
}