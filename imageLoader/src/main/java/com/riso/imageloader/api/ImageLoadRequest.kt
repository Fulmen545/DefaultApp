package com.riso.imageloader.api

import android.widget.ImageView
import androidx.annotation.DrawableRes

data class ImageLoadRequest(
    val url: String,
    @DrawableRes val placeholderRes: Int,
    val target: ImageView
)