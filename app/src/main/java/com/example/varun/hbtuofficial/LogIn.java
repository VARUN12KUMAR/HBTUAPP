package com.example.varun.hbtuofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    EditText name11,email12,pass12;
    Button b2;
    TextView forgot,create;
    FirebaseAuth auth;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email12=(EditText)findViewById(R.id.email2);
        pass12=(EditText)findViewById(R.id.pass2);
        b2=(Button)findViewById(R.id.logIn);
        pd=new ProgressDialog(this);
        forgot=(TextView)findViewById(R.id.forgot);
        create=(TextView)findViewById(R.id.CreateAcc);
        auth=FirebaseAuth.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,Register.class));
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,Resetpass.class));

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 logIn();
            }
        });
    }

    private void logIn() {
        String ema1,paas1;
        ema1=email12.getText().toString();
        paas1=pass12.getText().toString();
        if(ema1.isEmpty()||paas1.isEmpty()){
            Toast.makeText(this, "Fill Your Detail", Toast.LENGTH_SHORT).show();
            return;
        }
        pd.setMessage("LogIN....");
        pd.show();

        auth.signInWithEmailAndPassword(ema1,paas1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    Toast.makeText(LogIn.this, "Log In SuccessFull", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LogIn.this, "Log Failed", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }
}
