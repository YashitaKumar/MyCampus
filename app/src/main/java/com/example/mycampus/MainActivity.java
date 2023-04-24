package com.example.mycampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView login,register,selected;
    FrameLayout frameLayout;
    SwipeListener swipeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views in Layout
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        selected=findViewById(R.id.select);
        frameLayout=findViewById(R.id.fragment_container);

        //Variable initialiation
        replaceFrgament(new LoginFragment());
        swipeListener=new SwipeListener(frameLayout);

        //On Click Listeners
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.login: selected.animate().x(0).setDuration(100);
                             replaceFrgament(new LoginFragment());
                             break;
            case R.id.register: int size=register.getWidth();
                                selected.animate().x(size).setDuration(100);
                                replaceFrgament(new RegisterFragment());
                                break;


        }
    }

    public void replaceFrgament(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).setReorderingAllowed(true).addToBackStack("name").commit();
    }
    public class SwipeListener implements View.OnTouchListener{

        GestureDetector gestureDetector;
        public SwipeListener(View view) {
            int threshold = 0;
            int velocity_threshold= 0;


            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float xDiff = e2.getX() - e1.getX();
                    float yDiff = e2.getY() - e1.getY();

                    if(Math.abs(xDiff) > Math.abs(yDiff))
                    {
                        if(Math.abs(xDiff)>threshold && Math.abs(velocityX)> velocity_threshold){
                            if(xDiff>0){
                                //Swipe Right
                                selected.animate().x(0).setDuration(100);
                                replaceFrgament(new LoginFragment());
                            }
                            else
                            {
                                //Swipe Left
                                int size=register.getWidth();
                                selected.animate().x(size).setDuration(100);
                                replaceFrgament(new RegisterFragment());
                            }
                        }
                    }

                    return false;
                }
            };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }
}