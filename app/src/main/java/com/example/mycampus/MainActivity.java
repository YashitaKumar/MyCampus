package com.example.mycampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView login,register,selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFrgament(new LoginFragment());

        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        selected=findViewById(R.id.select);

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
}