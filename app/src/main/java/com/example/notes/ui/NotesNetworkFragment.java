package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notes.DisplayingTheDescriptionOfNotes;
import com.example.notes.NoteDescriptionFragment;
import com.example.notes.NoteTitleFragment;
import com.example.notes.R;
import com.example.notes.data.CardData;
import com.example.notes.data.CardsSource;
import com.example.notes.data.CardsSourceImpl;

import org.jetbrains.annotations.NotNull;


public class NotesNetworkFragment extends Fragment {

    private CardsSource data;
    private NotesNetworkAdapter adapter;
    private RecyclerView recyclerView;
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
        data = new CardsSourceImpl(getResources()).init();

        initView(view);

        setHasOptionsMenu(true);
        return view;

    }


    //создаём cards_menu меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    //обработка нажатия cards_menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                data.addCardData(new CardData("Заголовок " + data.size(), "Описание " + data.size(), R.drawable.ak_74, false));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        data = new CardsSourceImpl(getResources()).init();
        initRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
    private void initRecyclerView() {
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        adapter = new NotesNetworkAdapter(data, this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);


        // Установим слушателя
        adapter.SetOnItemClickListener((view, position) -> {


            showNoteAndData(position);

            isLandscape = getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE;

            if (isLandscape) {
                showLandNoteAndData(position);
            }

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
        NoteDescriptionFragment displayNotes = NoteDescriptionFragment.newInstance(num);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.landDisplayDescriptAndData, displayNotes).commit();
    }

    public void showPortNoteAndData(int num) {
        Intent intent = new Intent(getActivity(), DisplayingTheDescriptionOfNotes.class);
        intent.putExtra(NoteDescriptionFragment.ARG_INDEX, num);
        startActivity(intent);

    }

    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.contex_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_update:
                Toast.makeText(getContext(), "Update your app", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(getContext(), "Delete your cardView", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
