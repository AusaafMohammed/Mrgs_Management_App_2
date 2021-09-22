package com.example.mrgsmanagementapp;

//These are the imports for PlannerActivity.java
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PlannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_planner);
    }

    public void openApp(View view){
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.calendar");
        if(intent != null){
            startActivity(intent);
        } else{
            Toast.makeText(PlannerActivity.this, "There is no package", Toast.LENGTH_LONG).show();
        }
    }

}