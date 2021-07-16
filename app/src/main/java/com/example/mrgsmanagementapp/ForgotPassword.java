package com.example.mrgsmanagementapp;

//These are the imports for ForgotPassword.java
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

//  These are for creating variables for Authentication and EditText
    FirebaseAuth mAuth;
    private EditText ETEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

//      Assigning Variables with id's defined in activity_forgot.xml
        ETEmail =findViewById(R.id.edittextEmail);
        Button btnResetPassword = findViewById(R.id.btnResetPassword);
        mAuth=FirebaseAuth.getInstance();

//      This part runs the resetPassword on click of ResetPassword button
        btnResetPassword.setOnClickListener(v -> resetPassword());
    }

//  Method for resetPassword
//  This part ensures that the user enters an email address and also to ensure that the email address entered is valid or not
    private void resetPassword() {
        String email = Objects.requireNonNull(Objects.requireNonNull(ETEmail.getText()).toString().trim());

        if(email.isEmpty()){
//          If email is not entered
            ETEmail.setError("Email is required!");
            ETEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//          If email entered is incorrect
            ETEmail.setError("Please provide valid email! Please provide your account email!");
            ETEmail.requestFocus();
            return;
        }

//      If email matches, this part lets the user know if the reset link was sent or no
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
//              If the reset link was sent
                Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
            }else{
//              If the reset link was not sent
                Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });
    }
//ForgotPassword Part Ends
}