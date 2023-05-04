package com.example.mycampus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycampus.AllClubRelatedModels.EventsModel;
import com.example.mycampus.databinding.ActivityCreateEventBinding;
import com.example.mycampus.databinding.ActivityUploadingFileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {
    EditText name,longD,eventD,eventT,eventL,eventDur;
    String eventName,eventDate,eventTime,eventVenue,eventdescp,eventDuration,pic,posistion,id,eventClub;
    int eventLikes = 0;
    int dur;
    long pos;
    Button submit;
    String fileName;

    ActivityCreateEventBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        binding =  ActivityCreateEventBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        binding.selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();

            }
        });

        binding.uploadimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();
            }
        });

        //Views
        name = findViewById(R.id.eventName);
        longD = findViewById(R.id.eventLong);
        eventD = findViewById(R.id.eventD);
        eventT = findViewById(R.id.eventT);
        eventDur = findViewById(R.id.eventDur);
        eventL = findViewById(R.id.eventL);
        submit = findViewById(R.id.create);

        id=getIntent().getStringExtra("id");
        eventClub=getIntent().getStringExtra("club");
        pos=getIntent().getLongExtra("posistion",0);
        posistion= Long.toString(pos);

        fileName = id+"_"+posistion;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventName = name.getText().toString();
                eventDate = eventD.getText().toString();
                eventTime = eventT.getText().toString();
                eventVenue = eventL.getText().toString();
                eventdescp = longD.getText().toString();
                eventDuration = eventDur.getText().toString();
//                pic = "https://firebasestorage.googleapis.com/v0/b/let-em-glide.appspot.com/o/images%2F200220_5?alt=media&token=fc7ec476-150f-433b-bb7a-577fbc5e0f61";
                FirebaseStorage.getInstance().getReference("images/"+fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pic=uri.toString();
                        dur=Integer.parseInt(eventDuration);
                        EventsModel eventsModel = new EventsModel(eventName,eventClub,eventTime,eventDate,pic,eventVenue,eventdescp,eventLikes,dur);

                        FirebaseDatabase.getInstance().getReference("/Events/"+posistion).setValue(eventsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                    Toast.makeText(CreateEventActivity.this,"Class created",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(CreateEventActivity.this,"Failed attempt",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });


            }
        });



    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();

        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        binding.firebaseimage.setImageURI(null);
                        Toast.makeText(CreateEventActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(CreateEventActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data !=null && data.getData() !=null) {

            imageUri = data.getData();
            binding.firebaseimage.setImageURI(imageUri);

        }
    }
}