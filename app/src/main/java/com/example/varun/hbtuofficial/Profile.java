package com.example.varun.hbtuofficial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    ImageView profileImage;
    TextView profileName,profileEmail,profilePass;
    Button updateButton;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage=(ImageView)findViewById(R.id.image2);
        profileName=(TextView)findViewById(R.id.name1111);
        profileEmail=(TextView)findViewById(R.id.email2222);
        profilePass=(TextView)findViewById(R.id.pass3333);
        updateButton=(Button)findViewById(R.id.editProf);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(auth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDataInfo userProfile=dataSnapshot.getValue(UserDataInfo.class);
                profileName.setText("Name:"+   userProfile.getName());
                profileEmail.setText("Email:"+ userProfile.getEmail());
                profilePass.setText("pass:"+   userProfile.getPass());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
