package com.example.mycampus;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycampus.Adapters.ClubShortAdapter;
import com.example.mycampus.Adapters.EventShortAdapter;
import com.example.mycampus.AllClubRelatedModels.ClubModel;
import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.Models.DhabbaItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingEventsFragment extends Fragment {
    RecyclerView recyclerView;
    List<EventsModel> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpcomingEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingEventsFragment newInstance(String param1, String param2) {
        UpcomingEventsFragment fragment = new UpcomingEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView= inflater.inflate(R.layout.fragment_upcoming_events, container, false);

        recyclerView = mainView.findViewById(R.id.AllUpcomingEvents);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventShortAdapter eventShortAdapter = new EventShortAdapter();
        recyclerView.setAdapter(eventShortAdapter);

        FirebaseDatabase.getInstance().getReference("/Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    String eventClub= data.child("eventClub").getValue().toString();
                    String eventDate= data.child("eventDate").getValue().toString();
                    String Likes=data.child("eventLikes").getValue().toString();
                    int eventLikes = Integer.parseInt(Likes);
                    String eventName= data.child("eventName").getValue().toString();
                    String eventPic=data.child("eventPic").getValue().toString();
                    String eventTime = data.child("eventTime").getValue().toString();
                    String eventVenue = data.child("eventVenue").getValue().toString();
                    EventsModel eventsModel = new EventsModel(eventName,eventClub,eventTime,eventDate,eventPic,eventVenue,eventLikes);
                    list.add(eventsModel);
                }
                eventShortAdapter.setList(list);
                eventShortAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return mainView;
    }
}