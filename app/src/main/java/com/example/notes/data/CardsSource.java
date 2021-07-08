package com.example.notes.data;

public interface CardsSource {
    CardData getCardData(int position);

    int size();

    void deleteCardData(int position);

    void addCardData(CardData cardData);

    void clearCardData();

    void updateCardData(int position, CardData cardData);
}
