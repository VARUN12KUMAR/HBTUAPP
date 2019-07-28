package com.example.varun.hbtuofficial;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    CardView branchCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        branchCard=(CardView)findViewById(R.id.cardBranch);
        branchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DetailActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id=item.getItemId();
       if(id==R.id.Home){
           startActivity(new Intent(MainActivity.this,MainActivity.class));
       }
       else if(id==R.id.profile){
           startActivity(new Intent(MainActivity.this,Profile.class));

       }
       else if(id==R.id.logout){
           startActivity(new Intent(MainActivity.this,LogIn.class));


       }


        return super.onOptionsItemSelected(item);
    }
}
