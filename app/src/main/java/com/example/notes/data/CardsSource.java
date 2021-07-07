package com.example.notes.data;

public interface CardsSource {
    CardData getCardData(int position);
    int size();
}
