package com.example.firebase_authentication_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Practice_Activity extends AppCompatActivity {

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);


        database=FirebaseDatabase.getInstance();

      DatabaseReference  photos=database.getReference("Photos").child("User 1");
      DatabaseReference  Videos=database.getReference("Videos").child("User 1");
      photos.setValue("photos.jpg");
      Videos.setValue("video.mp3");



    }
}