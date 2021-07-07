package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.NoteTitleFragment;
import com.example.notes.R;
import com.example.notes.data.CardsSource;
import com.example.notes.data.CardsSourceImpl;


public class NotesNetworkFragment extends Fragment {
//    NoteTitleFragment noteTitleFragment = new NoteTitleFragment();

    public static NotesNetworkFragment newInstance() {
        return new NotesNetworkFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_title, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        CardsSource data = new CardsSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;

    }

    @SuppressLint("DefaultLocale")
    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NotesNetworkAdapter adapter = new NotesNetworkAdapter(data);
        recyclerView.setAdapter(adapter);

        // Установим слушателя
        adapter.SetOnItemClickListener(new NotesNetworkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Position - %d", position), Toast.LENGTH_SHORT).show();
//                Log.d("log", position+"");
            }
        });

    }
//        noteTitleFragment.showPortNoteAndData(position));

}
