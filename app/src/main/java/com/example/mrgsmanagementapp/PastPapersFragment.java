package com.example.mrgsmanagementapp;

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

public class PastPapersFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_papers,container,false);

        Button btnPastPapers = view.findViewById(R.id.btnPastPapers);

        btnPastPapers.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),PastPapersActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
