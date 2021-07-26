package com.example.notes.data

import com.google.firebase.Timestamp
import java.util.*

object CardDataMapping {
    @JvmStatic
    fun toCardData(id: String?, doc: Map<String?, Any?>): CardData {
        val indexPic = doc[Fields.PICTURE] as Long
        val timeStamp = doc[Fields.DATE] as Timestamp?
        val answer = CardData(
            doc[Fields.TITLE] as String?,
            doc[Fields.DESCRIPTION] as String?,
            PictureIndexConverter.getPictureByIndex(indexPic.toInt()),
            doc[Fields.LIKE] as Boolean,
            timeStamp!!.toDate()
        )
        answer.id = id
        return answer
    }

    @JvmStatic
    fun toDocument(cardData: CardData): Map<String, Any?> {
        val answer: MutableMap<String, Any?> = HashMap()
        answer[Fields.TITLE] = cardData.title
        answer[Fields.DESCRIPTION] = cardData.description
        answer[Fields.PICTURE] =
            PictureIndexConverter.getIndexByPicture(cardData.picture)
        answer[Fields.LIKE] = cardData.isLike
        answer[Fields.DATE] = cardData.date
        return answer
    }

    object Fields {
        const val PICTURE = "picture"
        const val DATE = "date"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val LIKE = "like"
    }
}