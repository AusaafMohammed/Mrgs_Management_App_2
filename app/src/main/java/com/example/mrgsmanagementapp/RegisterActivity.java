package com.example.mrgsmanagementapp;

//These are the imports for RegistrationActivity.java
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

//  These are for creating variables
    private TextInputLayout inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//      Assigning Variables with id's defined in activity_register.xml
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);

//      These are for LoadingBar and Authentication
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(this);

//      This part runs the AttemptRegistration on click of Register button
        btnRegister.setOnClickListener(v -> AttemptRegistration());
    }

//  Method for AttemptRegistration
//  This part ensures that the user enters valid email and valid password and also confirmation of password matches the password or not
    private void AttemptRegistration() {
        String email= Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        String password= Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
        String confirmPassword= Objects.requireNonNull(inputConfirmPassword.getEditText()).getText().toString();

        if (email.isEmpty() || !email.contains("@students.mrgs.school.nz"))
        {
//          Show error method created which lets the user know if the email is valid or not
            showError(inputEmail,"Email is not Valid!");

        }else if(password.isEmpty() || password.length()<8)
        {
//          Show error method which lets the user know if the password entered is 8 digits or no
//          I coded it 8 digits password so that the user can have a strong and more secure password
            showError(inputPassword,"Password must be greater than 8 digits!");
        }else if (!confirmPassword.equals(password))
        {
//          Shows error if the users password and confirm password doesn't match
            showError(inputConfirmPassword,"Password Does not match!");
        }
//        If the email, password and confirm password entered meet the requirements
        else
        {
//          Loading bar which informs the user if the authentication was successful or no
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please Wait, Loading soon!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
//          If Registration was successful, It loads MainActivity
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
//              If Registration was not successful
                else
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration is Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

//  This Method is for showing errors for Registration interface
    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
//Registration Part Ends
}