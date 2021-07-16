package com.example.mrgsmanagementapp;

//These are the imports for PlannerFragment.java
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class PlannerFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//      This is for viewing fragment_planner.xml
        View view = inflater.inflate(R.layout.fragment_planner,container,false);

//      Assigning Variables with id's defined in fragment_planner.xml
        Button btn_planner = view.findViewById(R.id.btn_planner);

//      if btn_planner is clicked, it redirects the user to PlannerActivity
        btn_planner.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),PlannerActivity.class);
            startActivity(intent);
        });
        return view;
    }
//PlannerFragment Part Ends
}
