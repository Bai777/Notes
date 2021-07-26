package com.example.notes.data

import com.example.notes.R
import java.util.*

object PictureIndexConverter {
    private val random = Random()
    private val syncObj = Any()
    private val picIndex = intArrayOf(
        R.drawable.ak_74,
        R.drawable.beretta,
        R.drawable.aps,
        R.drawable.gsh_18,
        R.drawable.pps_1,
        R.drawable.svd,
        R.drawable.vss
    )

    @JvmStatic
    fun randomPictureIndex(): Int {
        synchronized(syncObj) { return random.nextInt(picIndex.size) }
    }

    @JvmStatic
    fun getPictureByIndex(index: Int): Int {
        var index = index
        if (index < 0 || index >= picIndex.size) {
            index = 0
        }
        return picIndex[index]
    }

    fun getIndexByPicture(picture: Int): Int {
        for (i in picIndex.indices) {
            if (picIndex[i] == picture) {
                return i
            }
        }
        return 0
    }
}