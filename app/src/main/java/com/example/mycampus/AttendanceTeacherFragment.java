package com.example.mycampus;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycampus.Models.ClassModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttendanceTeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceTeacherFragment extends Fragment implements View.OnClickListener {

    String idVal;
    FloatingActionButton addButton;
    AlertDialog dialog;

    public AttendanceTeacherFragment(String idVal) {
        this.idVal = idVal;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AttendanceTeacherFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendanceTeacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendanceTeacherFragment newInstance(String param1, String param2) {
        AttendanceTeacherFragment fragment = new AttendanceTeacherFragment();
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
        View view = inflater.inflate(R.layout.fragment_attendance_teacher, container, false);

        addButton = view.findViewById(R.id.addClass);

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("Teachers").child(idVal).hasChild("Classes"))
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a Class");
        final View view2 = getLayoutInflater().inflate(R.layout.add_class, null);
        Button button = view2.findViewById(R.id.Classsubmit);
        builder.setView(view2);
        dialog = builder.create();
        dialog.setContentView(view2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subject,batch,id,teacher;
                String subjectName,batchVal,classid,teacherName;
                Map<String, Object> map = new HashMap<>();


                subject = view2.findViewById(R.id.subject);
                batch = view2.findViewById(R.id.batch);
                id = view2.findViewById(R.id.classId);
                teacher = view2.findViewById(R.id.teacherName);

                subjectName=subject.getText().toString();
                batchVal=batch.getText().toString();
                classid=id.getText().toString();
                teacherName=teacher.getText().toString();

                map.put("classId",classid);
                map.put("subject",subjectName);



                //Adding class in database
                ClassModel classModel = new ClassModel(classid,subjectName,teacherName,idVal,batchVal,0,0);
                FirebaseDatabase.getInstance().getReference("/Classes/"+classid).setValue(classModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                            Toast.makeText(getActivity(),"Class created",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(),"Failed attempt",Toast.LENGTH_SHORT).show();

                    }
                });

                //Adding class detail in teachers
                FirebaseDatabase.getInstance().getReference("/Teachers/"+idVal).child("Classes").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(),"Data inserted successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Data no inserted successfully",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        addButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.addClass: dialog.show();
                                break;

        }
    }

    private void DialogAddClass() {
        View view = getLayoutInflater().inflate(R.layout.add_class, null);
        EditText subject,batch,id,teacher;
        String subjectName,batchVal,classid,teacherName;
        Map<String, Object> map = new HashMap<>();


        subject = view.findViewById(R.id.subject);
        batch = view.findViewById(R.id.batch);
        id = view.findViewById(R.id.classId);
        teacher = view.findViewById(R.id.teacherName);

        subjectName=subject.getText().toString();
        batchVal=batch.getText().toString();
        classid=id.getText().toString();
        teacherName=teacher.getText().toString();

        map.put("classId",classid);
        map.put("subject",subjectName);



        //Adding class in database
        ClassModel classModel = new ClassModel(classid,subjectName,teacherName,idVal,batchVal,0,0);
        FirebaseDatabase.getInstance().getReference("/Classes/"+classid).setValue(classModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                    Toast.makeText(getActivity(),"Class created",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Failed attempt",Toast.LENGTH_SHORT).show();

            }
        });

        //Adding class detail in teachers
        FirebaseDatabase.getInstance().getReference("/Teachers/"+idVal).child("Classes").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(),"Data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Data no inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });


    }



}