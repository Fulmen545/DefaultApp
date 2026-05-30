package com.riso.defalutapp.di

import com.riso.defalutapp.ui.screen.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SplashViewModel(
            loadImageListUseCase = get(),
            failureFormatter = get(),
        )
    }


}
