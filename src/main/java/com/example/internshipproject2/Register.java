package com.example.internshipproject2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipproject2.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView signin;
    EditText regName , inputEmail , inputPassword , inputConformPassword , phone;
    TextView btnRegister;
    FirebaseAuth mAuth;
    String userId;
    FirebaseFirestore fstore;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signin = findViewById(R.id.signin);
        regName = findViewById(R.id.regName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        phone = findViewById(R.id.phoneno);
        inputConformPassword = findViewById(R.id.inputConformPassword);
        btnRegister = findViewById(R.id.btnRegister);
        
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account ...");

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String fullName = regName.getText().toString();
                String pho = phone.getText().toString();
                String confirepassword = inputConformPassword.getText().toString();
                
                if(email.isEmpty())
                {
                    inputEmail.setError("Email is Required.");
                    inputEmail.requestFocus();
                    return;
                }
                else if(password.isEmpty())
                {
                    inputPassword.setError("Password is Required.");
                    inputPassword.requestFocus();
                    return;
                }
                else if(fullName.isEmpty())
                {
                    regName.setError("Full Name is Required");
                    regName.requestFocus();
                    return;
                }
                else if(pho.isEmpty())
                {
                    phone.setError("Phone Number is Required");
                    phone.requestFocus();
                    return;
                }
                else if(password.length()< 6){
                    inputPassword.setError("Password Must Be Greater Than 6 Char");
                    inputEmail.requestFocus();
                    return;
                }
                else if(!confirepassword.equals(password))
                {
                    inputConformPassword.setError("Confirm Password");
                    inputConformPassword.requestFocus();
                }
                else 
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Users users = new Users(fullName,email , password , pho);
                                userId = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(userId).setValue(users);
                                Toast.makeText(Register.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                                DocumentReference documentReference = fstore.collection("Users").document(userId);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fName",fullName);
                                user.put("email",email);
                                user.put("Phone", pho);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess:user profile is created for "+ userId);
                                    }
                                });
                                Intent i= new Intent(Register.this,Home.class);
                                startActivity(i);
                            }
                            else 
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });

        if(mAuth.getCurrentUser() !=null){
            Intent i= new Intent(Register.this,Home.class);
            startActivity(i);
            finish();
        }


        
    }
}