package com.example.notes.data

interface CardsSource {
    fun init(cardsSourceResponse: CardsSourceResponse?): CardsSource?
    fun getCardData(position: Int): CardData?
    fun size(): Int
    fun deleteCardData(position: Int)
    fun addCardData(cardData: CardData?)
    fun clearCardData()
    fun updateCardData(position: Int, cardData: CardData?)
}