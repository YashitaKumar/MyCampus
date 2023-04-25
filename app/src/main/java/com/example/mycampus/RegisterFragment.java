package com.example.mycampus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mycampus.Models.ClubHeadModel;
import com.example.mycampus.Models.StudentModel;
import com.example.mycampus.Models.TeacherModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    //view elements
    RadioGroup radioGroup;
    RadioButton studentRadio,teacherRadio;
    ConstraintLayout studentR,passR;
    EditText name,email,phone,password,passwordC,studentEnrollment,club,teacherId;
    CheckBox club_head;
    Button registerBtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        //Views in layout
        radioGroup = view.findViewById(R.id.optionsR);
        studentRadio = view.findViewById(R.id.btn1);
        teacherRadio = view.findViewById(R.id.btn2);
        studentR = view.findViewById(R.id.studentOptions);
        passR = view.findViewById(R.id.passwordFields);
        teacherId = view.findViewById(R.id.teacherid);
        name=view.findViewById(R.id.nameR);
        email=view.findViewById(R.id.emailR);
        phone=view.findViewById(R.id.phoneR);
        password=view.findViewById(R.id.password);
        passwordC = view.findViewById(R.id.confirmPass);
        studentEnrollment = view.findViewById(R.id.enrollment);
        club = view.findViewById(R.id.club);
        club_head=view.findViewById(R.id.clubHead);
        registerBtn= view.findViewById(R.id.registerBtn);

        mAuth=FirebaseAuth.getInstance();

        //Buttons
        studentRadio.setOnClickListener(this);
        teacherRadio.setOnClickListener(this);
        registerBtn.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        int size=studentR.getHeight();
        int size2=teacherId.getHeight();
        switch (view.getId())
        {
            case R.id.btn1: passR.setY(teacherId.getY());
                            teacherId.setVisibility(View.INVISIBLE);
                            passR.animate().translationYBy(size).setDuration(100);
                            studentR.setVisibility(View.VISIBLE);
                            break;
            case R.id.btn2: passR.setY(studentR.getY());
                            studentR.setVisibility(View.INVISIBLE);
                            passR.animate().translationYBy(size2).setDuration(100);
                            teacherId.setVisibility(View.VISIBLE);
                            break;
            case R.id.registerBtn: userRegister();
                                   break;
        }
    }

    private void createUser(String Email,String Password,String Phone,String Name)
    {
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Verification Email Sent", Toast.LENGTH_SHORT).show();
                            //Save Data after verification mail sent
                            if (radioGroup.getCheckedRadioButtonId() == R.id.btn1) {
                                String head, Enrollment, Club;
                                Enrollment = studentEnrollment.getText().toString();
                                Club = club.getText().toString();
                                if (club_head.isChecked())
                                    head = "True";
                                else
                                    head = "False";
                                int pNo, eNo;
                                pNo = Integer.valueOf(Phone);
                                eNo = Integer.valueOf(Enrollment);
                                //Club Heads
                                ClubHeadModel clubHeadModel = new ClubHeadModel(eNo,Club);
                                FirebaseDatabase.getInstance().getReference("/ClubHeads").child(Enrollment).setValue(clubHeadModel);

                                StudentModel studentModel = new StudentModel(Name, Email, Club, Password, head, pNo, eNo);
                                FirebaseDatabase.getInstance().getReference("/Students").child(Enrollment).setValue(studentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "Registration complete", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Registration Failed due to error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String Teacher_id;
                                Teacher_id = teacherId.getText().toString();
                                TeacherModel teacherModel = new TeacherModel(Name, Email, Password, Integer.valueOf(Phone), Integer.valueOf(Teacher_id));
                                FirebaseDatabase.getInstance().getReference("/Teachers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(teacherModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "Registration Completed", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void userRegister() {
        String emailPattern = ".*@bmu\\.edu\\.in.*";
        String passwordPattern =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        String Name,Email,Password,PasswordConfirm,Phone;
        Name=name.getText().toString();
        Email=email.getText().toString();
        Password=password.getText().toString();
        PasswordConfirm=passwordC.getText().toString();
        Phone=phone.getText().toString();
        if(!Email.matches(emailPattern))
        {
            Toast.makeText(getActivity(),"Please Enter Correct Email",Toast.LENGTH_SHORT).show();
        }
        else if(Password.isEmpty())
        {
            Toast.makeText(getActivity(),"Please Enter Password",Toast.LENGTH_SHORT).show();
        }
        else if(!Password.matches(passwordPattern))
        {
            Toast.makeText(getActivity(),"Password must be 8 characters long",Toast.LENGTH_SHORT).show();
        }
        else if(!Password.equals(PasswordConfirm))
        {
            Toast.makeText(getActivity(),"Password does not match",Toast.LENGTH_SHORT).show();
        }
        else {
            if (radioGroup.getCheckedRadioButtonId() == R.id.btn1) {
                String head, Enrollment, Club;
                Enrollment = studentEnrollment.getText().toString();
                Club = club.getText().toString();
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Students").hasChild(Enrollment))
                            Toast.makeText(getActivity(),"This user is already registerd",Toast.LENGTH_SHORT).show();
                        else if (snapshot.hasChild("ClubHeads")) {
                            Boolean flag=true;
                            for (DataSnapshot data : snapshot.child("ClubHeads").getChildren()) {
                                if (data.child("club").getValue().toString().equals(Club)) {
                                    flag = false;
                                    Toast.makeText(getActivity(), "Head registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                            if(flag)
                                createUser(Email,Password,Phone,Name);
                        }
                        else {
                            createUser(Email,Password,Phone,Name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else
            {
                createUser(Email,Password,Phone,Name);
            }
        }
    }

}