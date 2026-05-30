package com.riso.domain.usecase

import com.riso.domain.model.ImageDataModel
import com.riso.domain.repository.ImageListRepository
import com.riso.core.Result

class LoadImageListUseCase(private val repository: ImageListRepository) {

    suspend fun execute(): Result<List<ImageDataModel>> {
        return repository.loadImageList()
    }
}
