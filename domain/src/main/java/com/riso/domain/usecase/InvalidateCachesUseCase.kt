package com.riso.domain.usecase

import com.riso.imageloader.api.ImageLoader

class InvalidateCachesUseCase(private val imageLoader: ImageLoader) {

    suspend fun execute() {
        return imageLoader.clearAll()
    }
}