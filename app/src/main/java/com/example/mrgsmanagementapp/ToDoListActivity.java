package com.example.mrgsmanagementapp;

//These are the imports for ToDoListActivity.java
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

public class ToDoListActivity extends AppCompatActivity {

//  These are for creating variables
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
        
//      Assigning Variables with id's defined in activity_to_do_list.xml
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//      These are for LoadingBar and Authentication
        loader = new ProgressDialog(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

//      This is for the floating button to add new to do task or no
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
//      Method created for addTask
        floatingActionButton.setOnClickListener(v -> addTask());
    }

//  Method addTask created
    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

//      This part reads the input_file.xml file
        View myView = inflater.inflate(R.layout.input_file, null);
//      Views input_file in dialog box format
        myDialog.setView(myView);

//      Creates an AlertDialog format which views the input_file so the alert box is not cancellable
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

//      Assigning Variables with id's defined in input_file.xml
        final EditText task = myView.findViewById(R.id.task);
        final EditText description = myView.findViewById(R.id.description);
        Button save = myView.findViewById(R.id.btn_save);
        Button cancel = myView.findViewById(R.id.btn_cancel);

//      If cancel button clicked, the dialog box dismisses
        cancel.setOnClickListener(v -> dialog.dismiss());

//      If save button clicked
        save.setOnClickListener(v -> {
//          Turns the texts into string format
            String mTask = task.getText().toString().trim();
            String mDescription = description.getText().toString().trim();
            String id = reference.push().getKey();
            String date = DateFormat.getDateInstance().format(new Date());

//          if no text is entered  in task, it shows error that task is required
            if (TextUtils.isEmpty(mTask)){
                task.setError("Task Required");
                return;
            }
//          if description is left blank, it shows the error saying description is required
            if (TextUtils.isEmpty(mDescription)){
                description.setError("Description Required");
                return;
            }else {
//              if both are entered, then a loader appears informing user that the data is being added
                loader.setMessage("Adding your data");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

//              Creating object of Model pass
//              Strings used which were described above
                Model model = new Model(mTask,mDescription,id,date);
                assert id != null;
                reference.child(id).setValue(model).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
//                      If task has been entered successfully
                        Toast.makeText(ToDoListActivity.this, "Task has been inserted successfully", Toast.LENGTH_SHORT).show();
                    }else {
//                      If task failed, it lets the user know what the error was
                        String error = Objects.requireNonNull(task1.getException()).toString();
                        Toast.makeText(ToDoListActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                    }
//                  Loader is dismissed
                    loader.dismiss();
                });
            }
//          Dialog box is dismissed
            dialog.dismiss();
        });
//      shows dialog box until the dialog box is dismissed
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//      Building Firebase Recycler Options
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
//              To view retrieved_layout.xml
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout, parent, false);
                return  new MyViewHolder(view);
            }
        };

//      setting recyclerView adapter
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;

//      This is for creating constructors
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTask(String task){
            TextView taskTV = mView.findViewById(R.id.taskTv);
            taskTV.setText(task);
        }
        public  void setDescription(String desc){
            TextView descTV = mView.findViewById(R.id.descriptionTv);
            descTV.setText(desc);
        }
        public void setDate(){
            TextView textViewDate = mView.findViewById(R.id.dateTv);

            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

            textViewDate.setText(currentDate);
        }
    }

//  Method created for updateTask
    private void updateTask() {
//      Building of alert dialog and inflating the update_data layout file
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_data, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

//      Assigning variables with id's defined in xml file
        EditText mTask = view.findViewById(R.id.edit_task);
        EditText mDescription = view.findViewById(R.id.edit_description);

//      for setting the task and description
        mTask.setText(task);
        mTask.setSelection(task.length());
        mDescription.setText(description);
        mDescription.setSelection(description.length());

//      finding delete button and update button id's from xml file and replacing with the variables
        Button btn_delete = view.findViewById(R.id.btn_delete);
        Button btn_update = view.findViewById(R.id.btn_update);

//      This part of the code is for the functionality of the update button (this also lets the users know whether the date has been successfully updated or not)
        btn_update.setOnClickListener(v -> {
//          Changing task and description into strong format
            task = mTask.getText().toString().trim();
            description = mDescription.getText().toString().trim();

//          using string date format to show the date on the to do task
            String date = DateFormat.getDateInstance().format(new Date());

            Model model = new Model(task,description,key,date);

            reference.child(key).setValue(model).addOnCompleteListener(task -> {

//              letting the user know if the data has been updated successfully or not
                if (task.isSuccessful()){
                    Toast.makeText(ToDoListActivity.this, "Data has been updated successfully", Toast.LENGTH_SHORT).show();
                }else{
                    String err = Objects.requireNonNull(task.getException()).toString();
                    Toast.makeText(ToDoListActivity.this, "Update failed" + err, Toast.LENGTH_SHORT).show();
                }

            });

//          dismissing dialog box
            dialog.dismiss();

        });

//      This is for the functionality of the delete button (Informs user whether the task was deleted successfully or no)
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