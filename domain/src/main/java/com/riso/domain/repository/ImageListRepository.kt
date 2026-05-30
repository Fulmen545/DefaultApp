package com.riso.domain.repository

import com.riso.core.Result
import com.riso.domain.model.ImageDataModel

interface ImageListRepository {
    suspend fun loadImageList(): Result<List<ImageDataModel>>
}