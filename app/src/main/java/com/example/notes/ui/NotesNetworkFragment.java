package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notes.DisplayingTheDescriptionOfNotes;
import com.example.notes.NoteDescriptionFragment;
import com.example.notes.NoteTitleFragment;
import com.example.notes.R;
import com.example.notes.data.CardsSource;
import com.example.notes.data.CardsSourceImpl;




public class NotesNetworkFragment extends Fragment {

    private boolean isLandscape;

    public static NotesNetworkFragment newInstance() {
        return new NotesNetworkFragment();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NotesNetworkAdapter adapter = new NotesNetworkAdapter(data);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);


        // Установим слушателя
        adapter.SetOnItemClickListener((view, position) -> {
//            Toast.makeText(getContext(), String.format("Position - %d", position), Toast.LENGTH_SHORT).show();
//                Log.d("log", position+"");


            showNoteAndData(position);

            isLandscape = getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE;

            if (isLandscape) {
                showLandNoteAndData(position);
            }


//            Intent intent = new Intent(getActivity(), DisplayingTheDescriptionOfNotes.class);
//            intent.putExtra(NoteDescriptionFragment.ARG_INDEX, position);
//            startActivity(intent);


        });

    }

    private void showNoteAndData(int num) {
        if (isLandscape) {
            showLandNoteAndData(num);
        } else {
            showPortNoteAndData(num);
        }
    }

    public void showLandNoteAndData(int num) {
        Log.d("log", num+"");
        NoteDescriptionFragment displayNotes = NoteDescriptionFragment.newInstance(num);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.landDisplayDescriptAndData, displayNotes).commit();
    }

    public void showPortNoteAndData(int num) {
        Intent intent = new Intent(getActivity(), DisplayingTheDescriptionOfNotes.class);
        intent.putExtra(NoteDescriptionFragment.ARG_INDEX, num);
        startActivity(intent);

    }

}
