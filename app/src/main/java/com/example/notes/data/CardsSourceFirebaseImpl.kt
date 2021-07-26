package com.example.notes.data

import android.content.Context
import android.widget.Toast
import com.example.notes.data.CardDataMapping.toCardData
import com.example.notes.data.CardDataMapping.toDocument
import com.google.firebase.firestore.*
import java.util.*

class CardsSourceFirebaseImpl : CardsSource {
    var context: Context? = null

    // База данных Firestore
    private val store = FirebaseFirestore.getInstance()

    // Коллекция документов
    private val collection = store.collection(CARDS_COLLECTION)

    // Загружаемый список карточек
    private var cardsData: MutableList<CardData>? = ArrayList()
    override fun init(cardsSourceResponse: CardsSourceResponse?): CardsSource? {
        // Получить всю коллекцию, отсортированную по полю (дата)
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
            .addOnCompleteListener { task ->

                // При удачном считывании данных загрузим список карточек
                if (task.isSuccessful) {
                    cardsData = ArrayList()
                    for (document in task.result!!) {
                        val doc = document.data
                        val id = document.id
                        val cardData = toCardData(id, doc)
                        (cardsData as ArrayList<CardData>).add(cardData)
                    }
                    cardsSourceResponse!!.initialized(this@CardsSourceFirebaseImpl)
                } else {
                    Toast.makeText(context, task.exception as CharSequence?, Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "get failed with$e",
                    Toast.LENGTH_SHORT
                ).show()
            }
        return this
    }

    override fun getCardData(position: Int): CardData? {
        return cardsData!![position]
    }

    override fun size(): Int {
        return if (cardsData == null) {
            0
        } else cardsData!!.size
    }

    override fun deleteCardData(position: Int) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData!![position].id!!).delete()
        cardsData!!.removeAt(position)
    }

    override fun addCardData(cardData: CardData?) {
        // Добавить документ
        collection.add(toDocument(cardData!!))
            .addOnSuccessListener { documentReference -> cardData.id = documentReference.id }
    }

    override fun clearCardData() {
        for (cardData in cardsData!!) {
            collection.document(cardData.id!!).delete()
        }
        cardsData = ArrayList()
    }

    override fun updateCardData(position: Int, cardData: CardData?) {
        val id = cardData!!.id
        // Изменить документ по идентификатору
        collection.document(id!!).set(toDocument(cardData))
    }

    companion object {
        private const val CARDS_COLLECTION = "notes"
    }
}