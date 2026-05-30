package com.riso.data.framework.api

import com.riso.data.framework.api.entity.ImageData
import retrofit2.http.GET

interface RemoteApi {

    @GET("image_list.json")
    suspend fun getImageList(): List<ImageData>
}