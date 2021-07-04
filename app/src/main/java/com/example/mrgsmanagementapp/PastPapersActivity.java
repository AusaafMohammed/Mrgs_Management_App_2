package com.example.mrgsmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PastPapersActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> al;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_papers);

//        Button return2 = findViewById(R.id.Return2);
//
//        return2.setOnClickListener(v -> {
//            Intent intent = new Intent(PastPapersActivity.this,MainActivity2.class);
//            startActivity(intent);
//        });

        lv=findViewById(R.id.listview1);
        al=new ArrayList<String>();
        aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,al);
        lv.setAdapter(aa);
        al.add("google.com");
        al.add("facebook.com");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PastPapersActivity.this,WebViewActivity.class);
                String s=al.get(position);
                intent.putExtra("a",s);
                startActivity(intent);


            }
        });
    }
}