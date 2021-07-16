package com.example.mrgsmanagementapp;

//These are the imports for ToDoListFragment.java
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class ToDoListFragment  extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//      This is for viewing fragment_todolist.xml
        View view = inflater.inflate(R.layout.fragment_todolist,container,false);

//      Assigning Variables with id's defined in fragment_todolist.xml
        Button btn_todo = view.findViewById(R.id.btn_todo);

//      if btn_todo is clicked, it redirects the user to ToDoListActivity
        btn_todo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),ToDoListActivity.class);
            startActivity(intent);
        });
        return view;
    }
//ToDoListFragment Part Ends
}
