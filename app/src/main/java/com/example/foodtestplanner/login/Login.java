package com.example.foodtestplanner.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.Register;
import com.example.foodtestplanner.home.view.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView username, password, toregister;
    private Button login;
    private  Button guestbtn;

    @Override
    protected void onStart() {
        super.onStart();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        initializeViews();

        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.putExtra("guestMode", true);
                startActivity(intent);
                finish();
            }
        });
        guestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Home.class);
                startActivity(intent);
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                // Checking if email and password are empty or not
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticate user
                mAuth.signInWithEmailAndPassword(user, pass)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                getUserEmail(user, user -> Log.i("TAG", "onEmailReceived: " + user));
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Authentication failed. Please check your email and password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void initializeViews() {
        username = findViewById(R.id.usernamlogid);
        password = findViewById(R.id.passlogid);
        toregister = findViewById(R.id.toregisterid);
        login = findViewById(R.id.logidbtn);
        guestbtn=findViewById(R.id.guestBtn);
    }
    private String encodeEmailForFirebase(String email) {
        return email.replace(".",",");
    }

    public void getUserEmail(String email, OnLoginListner onLoginListener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String encodedEmail = encodeEmailForFirebase(email);
        databaseReference.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                onLoginListener.reciverMail(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
