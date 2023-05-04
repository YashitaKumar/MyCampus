package com.example.mycampus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText email,password,id;
    String emailVal,passwordVal;
    FirebaseAuth mAuth;
    Button loginBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.emailL);
        password = view.findViewById(R.id.passwordL);
        loginBtn=view.findViewById(R.id.loginBtn);
        id=view.findViewById(R.id.idVal);
        loginBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        emailVal=email.getText().toString();
        passwordVal = password.getText().toString();
        String emailPattern = ".*@bmu\\.edu\\.in.*";

        mAuth=FirebaseAuth.getInstance();

        if(emailVal.isEmpty())
        {
            Toast.makeText(getActivity(),"Please enter Email",Toast.LENGTH_SHORT).show();
        }
        else if(!emailVal.matches(emailPattern))
        {
            Toast.makeText(getActivity(),"Please enter BMU Email",Toast.LENGTH_SHORT).show();
        }
        else if(passwordVal.isEmpty())
        {
            Toast.makeText(getActivity(),"Please enter Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(emailVal,passwordVal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(mAuth.getCurrentUser().isEmailVerified()==true)
                        {
                            Toast.makeText(getActivity(),"LogIn successfull",Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String idVal = id.getText().toString();
                                    if(snapshot.child("Students").hasChild(idVal))
                                    {
                                        Intent intent = new Intent(getActivity(),UploadingFile.class);
                                        startActivity(intent);
                                    }
                                    else if(snapshot.child("Teachers").hasChild(idVal))
                                    {
                                        Intent intent = new Intent(getActivity(),AttendanceTeacherActivity.class);
                                        intent.putExtra("id",idVal);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(getActivity(),"Please Enter correct id",Toast.LENGTH_SHORT).show();


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Please verify your email first",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please Enter correct email and password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}