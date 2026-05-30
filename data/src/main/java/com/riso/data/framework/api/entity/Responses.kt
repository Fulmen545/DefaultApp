package com.riso.data.framework.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageData(
    @Json(name = "id")
    val id: Int,

    @Json(name = "imageUrl")
    val imageUrl: String
)