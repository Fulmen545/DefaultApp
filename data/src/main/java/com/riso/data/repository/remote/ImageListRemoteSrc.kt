package com.riso.data.repository.remote

import com.riso.data.framework.api.entity.ImageData
import com.riso.core.Result
interface ImageListRemoteSrc {
    suspend fun loadImages(): Result<List<ImageData>>
}