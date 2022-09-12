package com.example.realtimeschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button register;
    TextView signup;
    private EditText editTextFullname, editTextEmail, editTextPassword, Phone, location, address;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference();
        register =  findViewById(R.id.register);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = editTextFullname.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                final String phone = Phone.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                final String Location =location.getText().toString().trim();
                final String Address = address.getText().toString().trim();

                if (username.isEmpty()) {
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                } else if (email.isEmpty()) {
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                } else if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please provide valid email");
                    editTextEmail.requestFocus();
                    return;

                } else if (password.length() < 6) {
                    editTextPassword.setError("Password should be more than 6 characters");
                    editTextPassword.requestFocus();
                    return;
                } else if (phone.isEmpty()) {
                    Phone.setError("phone is required");
                    Phone.requestFocus();
                    return;
                } else if (Address.isEmpty()) {
                    address.setError("Address is required");
                    address.requestFocus();
                    return;
                }
                else if (Location.isEmpty()) {
                    location.setError("Location is required");
                    location.requestFocus();
                    return;
                }
                else {

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.VISIBLE);
                                String currentUserId=mAuth.getCurrentUser().getUid();

                                /*RootRef.child("Users").child(currentUserId).setValue("");*/
                                HashMap<String, String> users=new HashMap<>();
                                users.put("uid", currentUserId);
                                users.put("location", Location);
                                users.put("address", Address);
                                users.put("name", username);
                                users.put("email", email);
                                users.put("phone", phone);
                                users.put("password",password);

                                //specify if user is admin or normal user
                                users.put("isUser", "1");

                                //set path
                                userRef.child("User").child(currentUserId).setValue(users);
                                Toast.makeText(getApplicationContext(), "Account Created Successfully",Toast.LENGTH_SHORT).show();
                                //loadingbar.dismiss();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(Register.this, Login.class);
                                intent.putExtra("currentphone", phone);
                                startActivity(intent);
                                //startActivity(new Intent(getApplicationContext(),Login.class));

                            }
                            else{
                                String message=task.getException().toString();
                                Toast.makeText(getApplicationContext(), "Error : " + message, Toast.LENGTH_SHORT).show();
                                //loadingbar.dismiss();

                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });

                }
            }
        });
        //register.setOnClickListener(this);
        editTextFullname = (EditText) findViewById(R.id.fullname);
        editTextEmail    = (EditText) findViewById(R.id.email);
        Phone=findViewById(R.id.phone);
        editTextPassword = (EditText) findViewById(R.id.password);
        location=(EditText)findViewById(R.id.Location);
        address=(EditText)findViewById(R.id.Address);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }
}