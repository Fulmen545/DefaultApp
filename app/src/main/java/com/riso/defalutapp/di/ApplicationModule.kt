package com.riso.defalutapp.di

import com.riso.core.FailureFormatter
import com.riso.defalutapp.utils.FailureFormatterImpl
import org.koin.dsl.module

val applicationModule = module {

    single<FailureFormatter> {
        FailureFormatterImpl(context = get())
    }

}

