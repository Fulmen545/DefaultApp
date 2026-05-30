package com.riso.defalutapp.di

import com.riso.domain.usecase.LoadImageListUseCase
import org.koin.dsl.module

val domainModule = module {

    single { LoadImageListUseCase(repository = get()) }
}
