package com.example.firebase_authentication_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class read_data extends AppCompatActivity {

    TextView show_email;

    Button read_data;

    FirebaseDatabase database;

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);
        database=FirebaseDatabase.getInstance();

        mAuth=FirebaseAuth.getInstance();
        String id=mAuth.getCurrentUser().getUid();
        show_email=findViewById(R.id.read_email);
        read_data=findViewById(R.id.read_data);


        //  1ST CHILD IS USED TO GO INTO THE NODE FROM WHERE WE WANT TO READ THE DATA AND SECOND ONE IS USED TO READ FROM WHICH NODE UNDER THE USER NODE WE HAVE TO READ
        databaseReference=database.getReference().child("User").child(id);


        read_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            String data=snapshot.getValue().toString();
                            show_email.setText(data);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });
    }
}