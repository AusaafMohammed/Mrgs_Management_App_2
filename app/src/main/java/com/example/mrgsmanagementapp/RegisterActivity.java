package com.example.mrgsmanagementapp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(this);

        btnRegister.setOnClickListener(v -> AtemptRegistration());
    }

    private void AtemptRegistration() {
        String email= Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        String password= Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
        String confirmPassword= Objects.requireNonNull(inputConfirmPassword.getEditText()).getText().toString();

        if (email.isEmpty() || !email.contains("@students.mrgs.school.nz"))
        {
            showError(inputEmail,"Email is not Valid!");

        }else if(password.isEmpty() || password.length()<8)
        {
            showError(inputPassword,"Password must be greater than 8 digits!");
        }else if (!confirmPassword.equals(password))
        {
            showError(inputConfirmPassword,"Password Does not match!");
        }
        else
        {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please Wait, Loading soon!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, MainActivity2.class);
                    //Change mainactivity to setupactivity later
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration is Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}