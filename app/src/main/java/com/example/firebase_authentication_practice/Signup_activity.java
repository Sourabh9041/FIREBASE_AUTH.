package com.example.firebase_authentication_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_activity extends AppCompatActivity {

    EditText signup_username, signup_email, signup_password;
    Button signup_button;

    // Add this line to initialize the database
    FirebaseDatabase database;

    FirebaseAuth mAuth;

    TextView sign_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_username = findViewById(R.id.signup_username);
        signup_email = findViewById(R.id.email_id);
        signup_password = findViewById(R.id.signup_password);
        signup_button = findViewById(R.id.signup_button);
        sign_activity = findViewById(R.id.Already_have_Account);

        // Initialize the database
        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email_id = signup_email.getText().toString();
                String password = signup_password.getText().toString();

                mAuth.createUserWithEmailAndPassword(email_id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup_activity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();

                            // Generate a unique key for each user
                            String userId = database.getReference("User").push().getKey();

                            // Set the email ID under the generated user key
                            DatabaseReference users = database.getReference("User").child(userId).child("Email ID");
                            users.setValue(email_id);

                            Intent intent = new Intent(Signup_activity.this, Home_Activity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Signup_activity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        sign_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

