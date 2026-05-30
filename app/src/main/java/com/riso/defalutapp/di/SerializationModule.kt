package com.riso.defalutapp.di

import com.squareup.moshi.Moshi
import org.koin.dsl.module

val serializationModule = module {
    single<Moshi> {
        Moshi.Builder()
            .build()
    }
}
