package com.example.mrgsmanagementapp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.util.Patterns;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

//import org.jetbrains.annotations.NotNull;

import java.util.Objects;

//import java.util.regex.PatternSyntaxException;

public class ForgotPassword extends AppCompatActivity {

//    private TextInputLayout inputEmail;
    FirebaseAuth mAuth;
    private EditText edittextEmail;

    public ForgotPassword() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

//        inputEmail=findViewById(R.id.inputEmail);
        edittextEmail=findViewById(R.id.edittextEmail);
        Button btnResetPassword = findViewById(R.id.btnResetPassword);
        mAuth=FirebaseAuth.getInstance();


        //This part of the code is to return back to login interface
//        TextView Return = findViewById(R.id.Return);
//        Return.setOnClickListener(v -> {
//            Intent intent = new Intent(ForgotPassword.this,LoginActivity.class);
//            startActivity(intent);
//        });

//      For Forgot Password Button
        btnResetPassword.setOnClickListener(v -> resetPassword());
    }

//  Method for resetPassword
    private void resetPassword() {
        String email = Objects.requireNonNull(Objects.requireNonNull(edittextEmail.getText()).toString().trim());

        if(email.isEmpty()){
            edittextEmail.setError("Email is required!");
            edittextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edittextEmail.setError("Please provide valid email!");
            edittextEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}