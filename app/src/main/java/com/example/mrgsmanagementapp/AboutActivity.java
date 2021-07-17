package com.example.mrgsmanagementapp;

//These are the imports for AboutActivity.java
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    ImageView mrgs;
    ImageView kamar;
    ImageView nzqa;
    ImageView fb;
    ImageView ig;
    ImageView twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mrgs = findViewById(R.id.mrgs_logo);
        kamar = findViewById(R.id.kamar_logo);
        nzqa = findViewById(R.id.nzqa_logo);
        fb = findViewById(R.id.fb_logo);
        ig = findViewById(R.id.ig_logo);
        twitter = findViewById(R.id.twitter_logo);

        mrgs.setOnClickListener(v -> gotoUrl("https://www.mrgs.school.nz/"));
        kamar.setOnClickListener(v -> gotoUrl("https://kamarportal.mrgs.school.nz/index.php"));
        nzqa.setOnClickListener(v -> gotoUrl("https://login.nzqa.govt.nz/auth/realms/nzqa/protocol/openid-connect/auth?response_type=code&client_id=learner-extranet&scope=offline_access%20profile%20openid&state=xD8div830Iu04K_0otZiZ7v5ltzBVA_ZR5g5F24O36Y%3D&redirect_uri=https://secure.nzqa.govt.nz/for-learners/login/oauth2/code/login.nzqa.govt.nz&nonce=zXz9uO3cr7PU_ZIS77uBOqOCrFwKpnjZ3NZKmspe8TA"));
        fb.setOnClickListener(v -> gotoUrl("https://www.facebook.com/MountRoskillGS/"));
        ig.setOnClickListener(v -> gotoUrl("https://www.instagram.com/mountroskillgrammarschool/"));
        twitter.setOnClickListener(v -> gotoUrl("https://twitter.com/mountroskillgs?lang=en"));
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}