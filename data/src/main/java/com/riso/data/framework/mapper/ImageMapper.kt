package com.riso.data.framework.mapper

import com.riso.data.framework.api.entity.ImageData
import com.riso.domain.model.ImageDataModel

fun ImageData.toModel() = ImageDataModel(
    id = id,
    url = imageUrl
)