package com.example.notes.observe;

import com.example.notes.data.CardData;

// интерфейс наблюдателя
public interface Observer {
    void updateCardData(CardData cardData);
}
