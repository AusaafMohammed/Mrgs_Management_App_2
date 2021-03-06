package com.example.mrgsmanagementapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mrgsmanagementapp.ui.login.LibraryActivity;

import org.jetbrains.annotations.NotNull;

public class LibraryFragment extends Fragment {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//      This is for viewing fragment_library.xml
        View view = inflater.inflate(R.layout.fragment_library,container,false);

//      Assigning Variables with id's defined in fragment_library.xml
        Button btn_planner = view.findViewById(R.id.btn_library);

//      if btn_library is clicked, it redirects the user to LibraryActivity
        btn_planner.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LibraryActivity.class);
            startActivity(intent);
        });
        return view;
    }
//Library Fragment Part Ends
}