package com.example.notes.data

import android.content.res.Resources
import com.example.notes.R
import java.util.*

class CardsSourceImpl(resources: Resources) : CardsSource {
    private val dataSource: MutableList<CardData?>
    private val resources // ресурсы приложения
            : Resources

    override fun init(cardsSourceResponse: CardsSourceResponse?): CardsSource? {
        // строки заголовков из ресурсов
        val titles = resources.getStringArray(R.array.titles)
        // строки описаний из ресурсов
        val descriptions = resources.getStringArray(R.array.descriptions)
        // изображения
        val pictures = imageArray
        // заполнение источника данных
        for (i in descriptions.indices) {
            dataSource.add(
                CardData(
                    titles[i],
                    descriptions[i],
                    pictures[i],
                    false,
                    Calendar.getInstance().time
                )
            )
        }
        cardsSourceResponse?.initialized(this)
        return this
    }

    // Механизм вытаскивания идентификаторов картинок
    private val imageArray: IntArray
        private get() {
            val pictures = resources.obtainTypedArray(R.array.pictures)
            val length = pictures.length()
            val answer = IntArray(length)
            for (i in 0 until length) {
                answer[i] = pictures.getResourceId(i, 0)
            }
            return answer
        }

    override fun getCardData(position: Int): CardData? {
        return dataSource[position]
    }

    override fun size(): Int {
        return dataSource.size
    }

    override fun deleteCardData(position: Int) {
        dataSource.removeAt(position)
    }

    override fun addCardData(cardData: CardData?) {
        dataSource.add(cardData)
    }

    override fun clearCardData() {
        dataSource.clear()
    }

    override fun updateCardData(position: Int, cardData: CardData?) {
        dataSource[position] = cardData
    }

    init {
        dataSource = ArrayList(7)
        this.resources = resources
    }
}