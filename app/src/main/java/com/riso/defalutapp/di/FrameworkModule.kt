package com.riso.defalutapp.di

import com.riso.data.framework.remote.ImageListRemoteSrcImpl
import com.riso.data.repository.remote.ImageListRemoteSrc
import org.koin.dsl.module

val frameworkModule = module {

    single<ImageListRemoteSrc> {
        ImageListRemoteSrcImpl(
            networkCallFactory = get(),
            appDispatchers = get(),
            remoteApi = get()
        )
    }

}