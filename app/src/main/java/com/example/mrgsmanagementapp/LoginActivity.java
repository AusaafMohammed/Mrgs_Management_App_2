package com.example.mrgsmanagementapp;

//These are the imports for LoginActivity.java
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.view.View;
//import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
//import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

//  These are for creating variables
    private TextInputLayout inputEmail,inputPassword;
    Button btnLogin;
    TextView forgotPassword,CreateNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;
//    CheckBox remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//      Assigning Variables with id's defined in activity_login.xml
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        CreateNewAccount=findViewById(R.id.CreateNewAccount);
//        remember_me=findViewById(R.id.remember_me);

//      These are for LoadingBar and Authentication
        mLoadingBar=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

//      This section directs the user to forgot password page where the user can reset their password using their valid email
        forgotPassword=findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent (LoginActivity.this,ForgotPassword.class);
            startActivity(intent);
        });

//      This part of the code is for checkbox for remember me button
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember_me","");
        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

//      Login part starts

//      This is for redirecting the user to Main Activity if the user's authentication entered is correct
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

//      This is for redirecting the user to Register Activity if the user doesn't have an account
        CreateNewAccount.setOnClickListener(v -> {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
//      This part runs the AttemptLogin on click of login button
        btnLogin.setOnClickListener(v -> AttemptLogin());
    }

//  Method for AttemptLogin
//  This part ensures that the user enters valid email and valid password
    private void AttemptLogin() {
        String email= Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        String password= Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
        if (email.isEmpty() || !email.contains("@students.mrgs.school.nz"))
        {
//          Show error method created which lets the user know if the email is valid or not
            showError(inputEmail,"Email is not Valid! Please enter a valid Email!");

        }else if(password.isEmpty() || password.length()<8)
        {
//          Show error method which lets the user know if the password entered is 8 digits or no
//          I coded it 8 digits password so that the user can have a strong and more secure password
            showError(inputPassword,"Password must be greater than 8 digits!");
        }
//      If the email and password entered are valid
        else
        {
//          Loading bar which informs the user if the authentication was successful or no
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please Wait, Loading soon!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
//          If Authentication was successful, It loads MainActivity
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
//              If Authentication was not successful
                else
                {
                    mLoadingBar.dismiss();
                    Toast.makeText(this, "Login Failed! Email or Password entered is incorrect!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

//  This Method is for showing errors for Login interface
    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
//  Login Part Ends

//  This part of the code is for confirmation if the user wants to exit the app or no
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}