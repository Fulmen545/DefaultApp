package com.riso.data.repository

import com.riso.core.Result
import com.riso.core.map
import com.riso.data.framework.mapper.toModel
import com.riso.data.repository.remote.ImageListRemoteSrc
import com.riso.domain.model.ImageDataModel
import com.riso.domain.repository.ImageListRepository

class ImageListRepositoryImpl(
    private val imageListRemoteSrc: ImageListRemoteSrc,
) : ImageListRepository {

    override suspend fun loadImageList(): Result<List<ImageDataModel>> {
        return imageListRemoteSrc.loadImages().map { it.map { it.toModel() } }
    }
}