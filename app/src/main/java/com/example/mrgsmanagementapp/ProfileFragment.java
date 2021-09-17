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

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//      This is for viewing fragment_profile.xml
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

//      Assigning Variables with id's defined in fragment_profile.xml
        Button btn_planner = view.findViewById(R.id.btn_profile);

//      if btn_profile is clicked, it redirects the user to ProfileActivity
        btn_planner.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),ProfileActivity.class);
            startActivity(intent);
        });
        return view;
    }
//Profile Fragment Part Ends
}