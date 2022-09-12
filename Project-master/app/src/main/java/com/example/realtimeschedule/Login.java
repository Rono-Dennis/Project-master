package com.example.realtimeschedule;


import androidx.annotation.NonNull;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends Activity {
    private EditText email,password;
    private ProgressBar progressBar;
    private TextView forgetPassword;
    private FirebaseAuth firebaseAuth;
    String cphone;
    Button reg;
    CheckBox admin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cphone=getIntent().getStringExtra("currentphone");
        Button registerbtn = findViewById(R.id.register);
        Button loginbtn = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);
        forgetPassword=findViewById(R.id.forget);
        firebaseAuth = FirebaseAuth.getInstance();
        reg=(Button)findViewById(R.id.register);

        admin=(CheckBox) findViewById(R.id.adminCheck);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (admin.isChecked()){
                    startActivity(new Intent(getApplicationContext(), AdminTasks.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordDialog = new AlertDialog.Builder(v.getContext());
                passwordDialog.setTitle("Reset Password");
                passwordDialog.setMessage("Enter Your Email to Receive Reset Link");
                passwordDialog.setView(resetMail);


                passwordDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        try {


                            firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(Login.this, "Reset Link sent To Your Email", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Error! Reset Link is not sent" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(Login.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                passwordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                passwordDialog.create().show();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  tex_email = email.getText().toString();
                String tex_password = password.getText().toString();

                if (tex_email.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if (tex_password.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }

                if (tex_password.length() < 6) {
                    password.setError("Password must be more than 6 characters");
                    password.requestFocus();
                    return;
                }  else {
                    login(tex_email,tex_password);
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(login2Activity.this, Homepage.class);
//
//        startActivity(intent);
    }

    private void login(String tex_email, String tex_password) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(tex_email,tex_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("currentphones", cphone);
                    startActivity(intent);
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "You are not yet registered", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}