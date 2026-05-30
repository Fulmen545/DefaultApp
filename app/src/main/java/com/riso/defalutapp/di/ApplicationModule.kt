package com.riso.defalutapp.di

import com.riso.core.FailureFormatter
import com.riso.core.dispatcher.AppDispatchers
import com.riso.core.dispatcher.AppDispatchersImpl
import com.riso.defalutapp.utils.FailureFormatterImpl
import org.koin.dsl.module

val applicationModule = module {

    single<FailureFormatter> {
        FailureFormatterImpl(context = get())
    }

    single<AppDispatchers> { AppDispatchersImpl() }

}

