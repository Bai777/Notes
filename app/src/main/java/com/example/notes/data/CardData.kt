package com.example.notes.data

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

class CardData : Parcelable {
    var id : String? = null             // идентификатор записи
    var title   : String?               // заголовок
    private set
    var description : String?           // описание
    private set
    var picture   : Int                 // изображение
    private set
    var isLike    : Boolean             // флажок
    private set
    var date   : Date                   // дата
    private set

    constructor(title: String?, description: String?, picture: Int, like: Boolean, date: Date) {
        this.title = title
        this.description = description
        this.picture = picture
        isLike = like
        this.date = date
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        description = `in`.readString()
        picture = `in`.readInt()
        isLike = `in`.readByte().toInt() != 0
        date = Date(`in`.readLong())
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeInt(picture)
        dest.writeByte((if (isLike) 1 else 0).toByte())
        dest.writeLong(date.time)
    }


    companion object CREATOR : Creator<CardData> {
        override fun createFromParcel(parcel: Parcel): CardData {
            return CardData(parcel)
        }

        override fun newArray(size: Int): Array<CardData?> {
            return arrayOfNulls(size)
        }
    }
}