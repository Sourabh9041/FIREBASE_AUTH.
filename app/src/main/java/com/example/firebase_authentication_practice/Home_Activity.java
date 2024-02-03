package com.example.firebase_authentication_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home_Activity extends AppCompatActivity {

    TextView show_email;

    FirebaseDatabase firebaseDatabase;

    Button send_database;
    Button signout;
    Button read_act_btn;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        show_email=findViewById(R.id.show_email);

        Bundle bundle=getIntent().getExtras();
        String email=bundle.getString("email");
        show_email.setText(email);
       read_act_btn=findViewById(R.id.read_data_activity);

        send_database=findViewById(R.id.send_database);
        signout=findViewById(R.id.signout);

        send_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference photos=firebaseDatabase.getReference("Photos").child("Users").child("User 1");
                photos.setValue("Photos.jpg");
                DatabaseReference videos=firebaseDatabase.getReference("Videos").child("Users").child("USer 1");
                videos.setValue("videos.mp4");
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Home_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        read_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Home_Activity.this,read_data.class);
                startActivity(intent);
            }
        });






    }
}