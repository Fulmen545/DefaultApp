package com.riso.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoaderSrc {
    fun load(url: String, @DrawableRes placeholderRes: Int, target: ImageView)
    suspend fun invalidateAll()
}