package com.example.mrgsmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

public class EventActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private ProgressDialog loader2;

    private String key = "";
    private String event;
    private  String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        recyclerview = findViewById(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);

        loader2 = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();
        reference2 = FirebaseDatabase.getInstance().getReference().child("event").child(onlineUserID);

        floatingActionButton = findViewById(R.id.fab2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });

    }

    private void addEvent() {
        AlertDialog.Builder eventDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View eventView = inflater.inflate(R.layout.input_file_event, null);
        eventDialog.setView(eventView);

        AlertDialog dialog2 = eventDialog.create();
        dialog2.setCancelable(false);

        final EditText event = eventView.findViewById(R.id.event);
        final EditText description = eventView.findViewById(R.id.event_description);
        Button save = eventView.findViewById(R.id.saveBtn);
        Button cancel = eventView.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(v -> dialog2.dismiss());

        save.setOnClickListener(v -> {
            String mEvent = event.getText().toString().trim();
            String Description = description.getText().toString().trim();
            String id = reference2.push().getKey();
            String date = DateFormat.getDateInstance().format(new Date());

            if (TextUtils.isEmpty(mEvent)){
                event.setError("Event Required");
                return;
            }
            if (TextUtils.isEmpty(Description)){
                description.setError("Description Required");
                return;
            }
            else{
                loader2.setMessage("Adding your data");
                loader2.setCanceledOnTouchOutside(false);
                loader2.show();

                EventModel model = new EventModel(mEvent, Description, id, date);
                reference2.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> event) {
                        if (event.isSuccessful()){
                            Toast.makeText(EventActivity.this, "Event has been inserted successfully", Toast.LENGTH_SHORT).show();
                            loader2.dismiss();
                        } else {
                            String error = event.getException().toString();
                            Toast.makeText(EventActivity.this, "Failed: "+ error, Toast.LENGTH_SHORT).show();
                            loader2.dismiss();
                        }
                    }
                });
            }

            dialog2.dismiss();

        });

        dialog2.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<EventModel> options = new FirebaseRecyclerOptions.Builder<EventModel>()
                .setQuery(reference2, EventModel.class)
                .build();

        FirebaseRecyclerAdapter<EventModel, MyViewHolder> adapter = new FirebaseRecyclerAdapter<EventModel, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull EventModel model) {
                holder.setEvent(model.getEvent());
                holder.setDesc(model.getDescription());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout_event, parent,false);
                return new MyViewHolder(view);
            }
        };

        recyclerview.setAdapter(adapter);
        adapter.startListening();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setEvent(String event){
            TextView eventTextView = mView.findViewById(R.id.event_textview);
            eventTextView.setText(event);
        }

        public void setDesc(String desc){
            TextView descTextView = mView.findViewById(R.id.description_textview);
            descTextView.setText(desc);
        }
    }
}