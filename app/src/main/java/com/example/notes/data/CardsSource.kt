package com.example.notes.data;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse);
    CardData getCardData(int position);

    int size();

    void deleteCardData(int position);

    void addCardData(CardData cardData);

    void clearCardData();

    void updateCardData(int position, CardData cardData);
}
