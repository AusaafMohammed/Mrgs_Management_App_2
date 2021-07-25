package com.example.mrgsmanagementapp;

//These are the imports for SplashActivity.java
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView copyright_text;
    Animation move_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//      For moving copyright text animation
        copyright_text = findViewById(R.id.copyright);
        move_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        copyright_text.startAnimation(move_anim);

        //This removes our status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//      Running the splash screen and then redirecting it to LoginActivity
        Runnable runnable= () -> {
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        };

//      This is for how amount of period the splash screen should stay for... In my application, the splash screen stays for 3 seconds
        Handler handler=new Handler();
        handler.postDelayed(runnable,5000);
    }
}