package com.example.mrgsmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    WebView w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        w=findViewById(R.id.web);
        Intent intent = getIntent();
        Bundle b=intent.getExtras();
        String data=b.getString("a");
        w.loadUrl("https://"+data);
    }
}