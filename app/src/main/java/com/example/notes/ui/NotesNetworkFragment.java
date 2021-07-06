package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.NoteTitleFragment;
import com.example.notes.R;


public class NotesNetworkFragment extends Fragment {
    NoteTitleFragment noteTitleFragment = new NoteTitleFragment();

    public static NotesNetworkFragment newInstance() {
        return new NotesNetworkFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_title, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        String[] data = getResources().getStringArray(R.array.note_title);
        initRecyclerView(recyclerView, data);
        return view;

    }

    private void initRecyclerView(RecyclerView recyclerView, String[] data) {
    // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NotesNetworkAdapter adapter = new NotesNetworkAdapter(data);
        recyclerView.setAdapter(adapter);

        // Установим слушателя
        adapter.SetOnItemClickListener((view, position) ->
                Toast.makeText(getContext(), String.format("%s - %d", ((TextView)view).getText(), position), Toast.LENGTH_SHORT).show());
//        noteTitleFragment.showPortNoteAndData(position));
    }
}
