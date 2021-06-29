package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NoteTitleFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout)view;
        String[] notesTitle = getResources().getStringArray(R.array.note_title);
        for (int i = 0; i < notesTitle.length; i++) {
            String notes = notesTitle[i];
            TextView textViewNotesTitle = new TextView(getContext());
            textViewNotesTitle.setText(notes);
            textViewNotesTitle.setTextSize(20);
            linearLayout.addView(textViewNotesTitle);
        }
    }
}