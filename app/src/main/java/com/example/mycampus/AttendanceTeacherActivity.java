package com.example.mycampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

public class AttendanceTeacherActivity extends AppCompatActivity {
    String idVal;
    FrameLayout frameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_teacher);

        idVal = getIntent().getStringExtra("id");
        AttendanceTeacherFragment attendanceTeacherFragment = new AttendanceTeacherFragment(idVal);
        frameLayout=findViewById(R.id.fragment_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,attendanceTeacherFragment).setReorderingAllowed(true).addToBackStack("name").commit();

    }
}