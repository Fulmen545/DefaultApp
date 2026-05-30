package com.riso.defalutapp.startup

import android.content.Context
import androidx.startup.Initializer
import com.riso.defalutapp.di.apiModule
import com.riso.defalutapp.di.applicationModule
import com.riso.defalutapp.di.domainModule
import com.riso.defalutapp.di.frameworkModule
import com.riso.defalutapp.di.getNetworkModule
import com.riso.defalutapp.di.repositoryModule
import com.riso.defalutapp.di.serializationModule
import com.riso.defalutapp.di.viewModelModule
import com.riso.imageloader.di.imageLoaderModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return startKoin {

            androidContext(context.applicationContext)
            modules(
                viewModelModule,
                applicationModule,
                getNetworkModule(debug = true),
                frameworkModule,
                repositoryModule,
                domainModule,
                apiModule,
                serializationModule,
                imageLoaderModule
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return mutableListOf(ThreetenInitializer::class.java)
    }
}
