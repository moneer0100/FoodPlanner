package com.example.foodtestplanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtestplanner.home.view.Home;
import com.example.foodtestplanner.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText username, eemail, pass;
    private TextView register;
    private Button registerbtn;
    private String email, password;

    @Override
    public void onStart() {
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
        setContentView(R.layout.activity_register);

        // Initialize views
        username = findViewById(R.id.usersignuoid);
        eemail = findViewById(R.id.emailsignupid);
        pass = findViewById(R.id.passsignupid);
        register = findViewById(R.id.talreadyhaveaccountid);
        registerbtn = findViewById(R.id.registerid); // Correct ID for register button

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = eemail.getText().toString();
                password = pass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User registration successful
                                    Toast.makeText(Register.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Home.class);
                                    startActivity(intent);
                                    finish(); // Finish the current activity to prevent the user from going back to it
                                } else {
                                    // If sign in fails, display a message to the user
                                    Toast.makeText(Register.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class); // Correct class name
                startActivity(intent);
                finish();
            }
        });
    }
    private String encodeEmailForFirebase(String email) {
        return email.replace(".",",");
    }
    private void saveUserEmailToDatabase(String email) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String encodedEmail = encodeEmailForFirebase(email);
        databaseReference.child(encodedEmail).setValue(email)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "User email saved successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save user email: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
