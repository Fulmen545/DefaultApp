package com.riso.imageloader.api

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {
    fun load(
        url: String,
        @DrawableRes placeholderRes: Int,
        target: ImageView
    )

    suspend fun preload(url: String)

    suspend fun clearAll()
}