package com.example.mrgsmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
//import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
//import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


//        return_todo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_todolist,new MainActivity2()).commit();
//            }
//        });


public class ToDoListActivity extends AppCompatActivity {

    private Button Return2;

    private RecyclerView recyclerView;

    private DatabaseReference reference;

    private ProgressDialog loader;

    private String key = "";
    private String task;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        Return2=findViewById(R.id.Return2);

        Return2.setOnClickListener(v -> {
            Intent intent = new Intent(ToDoListActivity.this,MainActivity2.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader = new ProgressDialog(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> addTask());

    }

    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.input_file, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText task = myView.findViewById(R.id.task);
        final EditText description = myView.findViewById(R.id.description);
        Button save = myView.findViewById(R.id.btn_save);
        Button cancel = myView.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(v -> dialog.dismiss());

        save.setOnClickListener(v -> {
            String mTask = task.getText().toString().trim();
            String mDescription = description.getText().toString().trim();
            String id = reference.push().getKey();
            String date = DateFormat.getDateInstance().format(new Date());

            if (TextUtils.isEmpty(mTask)){
                task.setError("Task Required");
                return;
            }
            if (TextUtils.isEmpty(mDescription)){
                description.setError("Description Required");
                return;
            }else {
                loader.setMessage("Adding your data");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                Model model = new Model(mTask,mDescription,id,date);
                assert id != null;
                reference.child(id).setValue(model).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        Toast.makeText(ToDoListActivity.this, "Task has been inserted successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        String error = Objects.requireNonNull(task1.getException()).toString();
                        Toast.makeText(ToDoListActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                    loader.dismiss();
                });
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference, Model.class)
                .build();

        FirebaseRecyclerAdapter<Model, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Model, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull ToDoListActivity.MyViewHolder holder, int position, @NonNull @NotNull Model model) {
                holder.setDate();
                holder.setTask(model.getTask());
                holder.setDescription(model.getDescription());

                holder.mView.setOnClickListener(v -> {
                    key = getRef(position).getKey();
                    task = model.getTask();
                    description = model.getDescription();

                    updateTask();
                });

            }

            @NonNull
            @NotNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout, parent, false);
                return  new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTask(String task){
            TextView taskTectView = mView.findViewById(R.id.taskTv);
            taskTectView.setText(task);
        }

        public  void setDescription(String desc){
            TextView descTectView = mView.findViewById(R.id.descriptionTv);
            descTectView.setText(desc);
        }
        public void setDate(){
            TextView textViewDate = mView.findViewById(R.id.dateTv);

            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

            textViewDate.setText(currentDate);
        }
    }

    private void updateTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_data, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

        EditText mTask = view.findViewById(R.id.edit_task);
        EditText mDescription = view.findViewById(R.id.edit_description);

        mTask.setText(task);
        mTask.setSelection(task.length());

        mDescription.setText(description);
        mDescription.setSelection(description.length());

        Button btn_delete = view.findViewById(R.id.btn_delete);
        Button btn_update = view.findViewById(R.id.btn_update);

        btn_update.setOnClickListener(v -> {
            task = mTask.getText().toString().trim();
            description = mDescription.getText().toString().trim();

            String date = DateFormat.getDateInstance().format(new Date());

            Model model = new Model(task,description,key,date);

            reference.child(key).setValue(model).addOnCompleteListener(task -> {

                if (task.isSuccessful()){
                    Toast.makeText(ToDoListActivity.this, "Data has been updated successfully", Toast.LENGTH_SHORT).show();
                }else{
                    String err = Objects.requireNonNull(task.getException()).toString();
                    Toast.makeText(ToDoListActivity.this, "Update failed" + err, Toast.LENGTH_SHORT).show();
                }

            });

            dialog.dismiss();

        });

        btn_delete.setOnClickListener(v -> {
            reference.child(key).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(ToDoListActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                }else{
                    String err = Objects.requireNonNull(task.getException()).toString();
                    Toast.makeText(ToDoListActivity.this, "Failed to delete task!" + err, Toast.LENGTH_SHORT).show();
                }
            });

            dialog.dismiss();
        });

        dialog.show();
    }
}