package com.example.mycampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mycampus.Adapters.DhabbaAdapter;
import com.example.mycampus.Models.DhabbaItemModel;
import com.example.mycampus.Models.DhabbaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DhabbaActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    List<DhabbaModel> list;
    TextView maggie,smoothie,selected,urban;
    FrameLayout frameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhabba);

        recyclerView = findViewById(R.id.dhabbaItems);
        maggie=findViewById(R.id.maggie);
        smoothie = findViewById(R.id.smoothie);
        urban = findViewById(R.id.urban);
        frameLayout=findViewById(R.id.fragment_container);
        selected=findViewById(R.id.select);
        maggie.setOnClickListener(this);
        smoothie.setOnClickListener(this);
        urban.setOnClickListener(this);

        forMaggie();




    }

    void forMaggie()
    {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DhabbaAdapter dhabbaAdapter = new DhabbaAdapter();
        recyclerView.setAdapter(dhabbaAdapter);


        FirebaseDatabase.getInstance().getReference("/Dhabbas/MaggiePoint").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    GenericTypeIndicator<ArrayList<DhabbaItemModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<DhabbaItemModel>>(){};
                    String itemName= data.child("itemName").getValue().toString();
                    String itemPic=data.child("itemPic").getValue().toString();
                    DhabbaModel dhabbaModel=new DhabbaModel(itemName,itemPic,data.child("items").getValue(genericTypeIndicator));
                    list.add(dhabbaModel);
                }
                dhabbaAdapter.setList(list);
                dhabbaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void forSmoothie()
    {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DhabbaAdapter dhabbaAdapter = new DhabbaAdapter();
        recyclerView.setAdapter(dhabbaAdapter);


        FirebaseDatabase.getInstance().getReference("/Dhabbas/SmoothieZone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    GenericTypeIndicator<ArrayList<DhabbaItemModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<DhabbaItemModel>>(){};
                    String itemName= data.child("itemName").getValue().toString();
                    String itemPic=data.child("itemPic").getValue().toString();
                    DhabbaModel dhabbaModel=new DhabbaModel(itemName,itemPic,data.child("items").getValue(genericTypeIndicator));
                    list.add(dhabbaModel);
                }
                dhabbaAdapter.setList(list);
                dhabbaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void forUrban()
    {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DhabbaAdapter dhabbaAdapter = new DhabbaAdapter();
        recyclerView.setAdapter(dhabbaAdapter);


        FirebaseDatabase.getInstance().getReference("/Dhabbas/UrbanTadka").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    GenericTypeIndicator<ArrayList<DhabbaItemModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<DhabbaItemModel>>(){};
                    String itemName= data.child("itemName").getValue().toString();
                    String itemPic=data.child("itemPic").getValue().toString();
                    DhabbaModel dhabbaModel=new DhabbaModel(itemName,itemPic,data.child("items").getValue(genericTypeIndicator));
                    list.add(dhabbaModel);
                }
                dhabbaAdapter.setList(list);
                dhabbaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.smoothie: int size=smoothie.getWidth();
                                selected.animate().x(size).setDuration(100);
                                forSmoothie();
                                break;
            case R.id.maggie:
                                selected.animate().x(0).setDuration(100);
                                forMaggie();
                                break;
            case R.id.urban: int size2 =urban.getWidth();
                            int size1=smoothie.getWidth();
                             selected.animate().x((size2+size1)).setDuration(100);
                             forUrban();
                            break;
        }
    }
}