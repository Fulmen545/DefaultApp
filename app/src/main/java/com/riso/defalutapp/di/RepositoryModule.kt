package com.riso.defalutapp.di

import com.riso.data.repository.ImageListRepositoryImpl
import com.riso.domain.repository.ImageListRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<ImageListRepository> {
        ImageListRepositoryImpl(
            imageListRemoteSrc = get()
        )
    }

}