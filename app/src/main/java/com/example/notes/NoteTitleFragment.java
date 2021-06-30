package com.example.notes;

import android.content.Intent;
import android.content.res.Configuration;
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
    public static final String CURRENT_NOTE = "CurrentNote";
    private int currentPosition = 0;
    private boolean isLandscape;

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
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notesTitle = getResources().getStringArray(R.array.note_title);
        for (int i = 0; i < notesTitle.length; i++) {
            String notes = notesTitle[i];
            TextView textViewNotesTitle = new TextView(getContext());
            textViewNotesTitle.setText(notes);
            textViewNotesTitle.setTextSize(30);
            linearLayout.addView(textViewNotesTitle);
            final int NUM = i;
            textViewNotesTitle.setOnClickListener(v -> {
               currentPosition = NUM;
                showNoteAndData(currentPosition);
            });
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_NOTE, currentPosition);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        if (isLandscape) {
            showLandNoteAndData(currentPosition);
        }
    }

    private void showNoteAndData(int num) {
        if (isLandscape) {
            showLandNoteAndData(num);
        } else {
            showPortNoteAndData(num);
        }
    }

    private void showLandNoteAndData(int num) {
        NoteDescriptionFragment displayNotes = NoteDescriptionFragment.newInstance(num);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.landDisplayDescriptAndData, displayNotes).commit();
    }

    private void showPortNoteAndData(int num) {
        Intent intent = new Intent(getActivity(), DisplayingTheDescriptionOfNotes.class);
        intent.putExtra(NoteDescriptionFragment.ARG_INDEX, num);
        startActivity(intent);

    }
}