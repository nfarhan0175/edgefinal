package com.example.edgefinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    Button btn_signup,btn_login;
    EditText user,email,contact,pass;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();
        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        pass = findViewById(R.id.pass);
        btn_signup = findViewById(R.id.signup);
        btn_login = findViewById(R.id.login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("register");
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String User = user.getText().toString();
                String Email = email.getText().toString();
                String Contact = contact.getText().toString();
                String Pass = pass.getText().toString();

                if(TextUtils.isEmpty(User)){
                    Toast.makeText(signup.this, "enter User", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(signup.this, "enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Contact)){
                    Toast.makeText(signup.this, "enter Contact", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Pass)){
                    Toast.makeText(signup.this, "enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User created successfully, now save data in Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid(); // Get the unique ID of the user
                            // Create a new user object to save in the database
                            UserDetails userDetails = new UserDetails(User, Email, Contact);
                            // Save the user data to the Firebase Realtime Database
                            databaseReference.child(userId).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Show success message and navigate to login screen
                                        Toast.makeText(signup.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(signup.this, MainActivity.class));
                                    } else {
                                        // Show error message if database save fails
                                        Toast.makeText(signup.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // If authentication fails, show error message
                            Toast.makeText(signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}