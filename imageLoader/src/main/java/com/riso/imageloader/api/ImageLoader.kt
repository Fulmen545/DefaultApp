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

    //added for Java
    fun preloadAsync(
        url: String,
        onSuccess: OnSuccessCallback,
        onError: OnErrorCallback
    )

    fun clearAllAsync(
        onSuccess: OnSuccessCallback,
        onError: OnErrorCallback
    )
}