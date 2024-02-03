package com.example.firebase_authentication_practice;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button google, signin;

    TextView signin_clickhere;

    EditText signin_email, signin_password;


    FirebaseDatabase database;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        google = findViewById(R.id.Google);
        signin_clickhere = findViewById(R.id.signin_clickhere);
        signin = findViewById(R.id.signin);
        signin_email = findViewById(R.id.signin_email);
        signin_password = findViewById(R.id.signin_password);

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        signin_clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Signup_activity.class);
                startActivity(intent);
            }
        });

        FirebaseUser current_user=mAuth.getCurrentUser();

        if (current_user !=null){

            Intent intent=new Intent(MainActivity.this,Home_Activity.class);
            intent.putExtra("email",current_user.getEmail());
            startActivity(intent);
            finish();
        }



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String emailId = signin_email.getText().toString();
                String password = signin_password.getText().toString();



                mAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Sign In succesfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, Home_Activity.class);
                            intent.putExtra("email", emailId);
                            startActivity(intent);

                           /* We have to get the iD of every new Registered user which is unique and pass the id
                             to Database reference path because if we didn't put the id everytime, When the new User
                             Signed up the OLD Id of User will be replaced by the NEW registered user. All EMAIL_ID will not be
                            added in the database that's why we have to store every user in unique id:s'*/

                            //===========================================================================
                            String id=mAuth.getCurrentUser().getUid();
                            //===========================================================================


                            DatabaseReference users=database.getReference("User").child(id).child("Email ID"); // PASS THE ID HERE

                            users.setValue(emailId);


                            DatabaseReference chats=database.getReference("Chats").child("User 1").child("chat");
                            chats.setValue("hey this is a chatMessage");




                        } else {

                            Toast.makeText(MainActivity.this, "Signin Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }

}