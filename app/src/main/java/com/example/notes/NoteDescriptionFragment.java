package com.example.notes;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NoteDescriptionFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private int index;


    public static NoteDescriptionFragment newInstance(int index) {
        NoteDescriptionFragment fragment = new NoteDescriptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_note_description, container, false);
        AppCompatTextView textViewNoteDescription = view.findViewById(R.id.textViewNoteDescription);
        AppCompatTextView textViewNoteCreateData = view.findViewById(R.id.textViewNoteCreateData);
        String[] noteDescription = getResources().getStringArray(R.array.note_description);
        String[] noteCreateData = getResources().getStringArray(R.array.date_the_note_was_created);
        textViewNoteDescription.setText(noteDescription[index]);
        textViewNoteCreateData.setText(noteCreateData[index]);
        return view;
    }
}