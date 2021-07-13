package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notes.DisplayingTheDescriptionOfNotes;
import com.example.notes.MainActivity;
import com.example.notes.Navigation;
import com.example.notes.NoteDescriptionFragment;
import com.example.notes.NoteTitleFragment;
import com.example.notes.R;
import com.example.notes.data.CardData;
import com.example.notes.data.CardsSource;
import com.example.notes.data.CardsSourceFirebaseImpl;
import com.example.notes.data.CardsSourceImpl;
import com.example.notes.data.CardsSourceResponse;
import com.example.notes.observe.Observer;
import com.example.notes.observe.Publisher;

import org.jetbrains.annotations.NotNull;


public class NotesNetworkFragment extends Fragment {

    private CardsSource data;
    private NotesNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isLandscape;
    private static final int MY_DEFAULT_DURATION = 2000;

    private Navigation navigation;
    private Publisher publisher;
    // признак, что при повторном открытии фрагмента
    // (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на последнюю запись
    private boolean moveToFirstPosition;


    public static NotesNetworkFragment newInstance() {
        return new NotesNetworkFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showLandNoteAndData(0);
        }

        View view = inflater.inflate(R.layout.fragment_note_title, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);


        initView(view);

        setHasOptionsMenu(true);
        data = new CardsSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsSource) {
               // adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }




    //создаём cards_menu меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    //обработка нажатия cards_menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
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
        adapter = new NotesNetworkAdapter(this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }



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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int itemId) {
        switch (itemId){
            case R.id.action_add:
                navigation.addFragment(CardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView
                        // перепрыгнул на начало списка
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_update:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(CardFragment.newInstance(data.getCardData(updatePosition)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.updateCardData(updatePosition, cardData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteCardData(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }
}
