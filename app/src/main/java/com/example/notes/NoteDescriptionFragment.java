package com.example.notes;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;


public class NoteDescriptionFragment extends Fragment {

    public static final String ARG_INDEX = "index";
    private int index;
    private DatePicker datePicker;
    private AppCompatTextView textViewNoteCreateData;


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


        View view = inflater.inflate(R.layout.fragment_note_description, container, false);
        AppCompatTextView textViewNoteDescription = view.findViewById(R.id.textViewNoteDescription);
        textViewNoteCreateData = view.findViewById(R.id.textViewNoteCreateData);
        String[] noteDescription = getResources().getStringArray(R.array.note_description);
//        String[] noteCreateData = getResources().getStringArray(R.array.date_the_note_was_created);
        textViewNoteDescription.setText(noteDescription[index]);
        //textViewNoteCreateData.setText(noteCreateData[index]);

        datePicker = view.findViewById(R.id.datePicker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.datePicker.init(year, month, day, this::datePickerChange);


        return view;
    }

    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth) {
//            Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
        textViewNoteCreateData.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }


}