package com.riso.defalutapp.di

import com.riso.data.framework.api.RemoteApi
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {
        getRetrofitClient().create(RemoteApi::class.java)
    }

}

fun Scope.getRetrofitClient() = get<Retrofit>()
