package com.riso.defalutapp.di

import com.riso.defalutapp.ui.screen.carList.CarListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CarListViewModel(
            loadImageListUseCase = get(),
            invalidateCachesUseCase = get(),
            failureFormatter = get(),
        )
    }


}
