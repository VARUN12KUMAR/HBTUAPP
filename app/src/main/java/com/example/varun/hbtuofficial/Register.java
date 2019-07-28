package com.example.varun.hbtuofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText name,email,pass;
    ImageView userprofile;
    Button b1;
    TextView haveAcc,signIn;
    FirebaseAuth auth;
    ProgressDialog pd1;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String na,em,pas;
    private int pick_img=123;
    Uri imgPath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.editperson1);
        email=(EditText)findViewById(R.id.email1);
        pd1=new ProgressDialog(this);
        pass=(EditText)findViewById(R.id.password1);
        userprofile =(ImageView)findViewById(R.id.image1);
        b1=(Button)findViewById(R.id.btnSignIn);
        haveAcc=(TextView)findViewById(R.id.Haveacc);
        signIn=(TextView)findViewById(R.id.signInGoogle);
        auth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,GoogleAuth.class));
            }
        });

        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg();
            }
        });


        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,LogIn.class));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

    }






    private void SignIn() {

        na=name.getText().toString();
        em=email.getText().toString();
        pas=pass.getText().toString();
        if(na.isEmpty()||em.isEmpty()||pas.isEmpty()||imgPath==null) {
            Toast.makeText(this, "fill Your Detail", Toast.LENGTH_SHORT).show();
            return;
        }

        pd1.setMessage("Registration......");
        pd1.show();
        auth.createUserWithEmailAndPassword(em,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userData();
                    Toast.makeText(Register.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this,LogIn.class));
                }
                else {
                    Toast.makeText(Register.this, "Register Not Successfull", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }
    public void userData(){
       FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
      DatabaseReference  databaseReference=firebaseDatabase.getReference(auth.getUid());
        StorageReference myref=storageReference.child(auth.getUid()).child("Images").child("profile pic");
        UploadTask uploadTask=myref.putFile(imgPath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "image not upload", Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Register.this, "image upload", Toast.LENGTH_SHORT).show();


            }
        });

        UserDataInfo userinfo= new UserDataInfo(na,em,pas);
        databaseReference.setValue(userinfo);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_img&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imgPath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imgPath);
                userprofile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void selectImg(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Sekect an Image"),pick_img);

    }

}
