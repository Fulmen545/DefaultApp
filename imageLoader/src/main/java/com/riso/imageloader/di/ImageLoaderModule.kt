package com.riso.imageloader.di

import com.riso.imageloader.api.ImageLoader
import com.riso.imageloader.cache.CacheInvalidator
import com.riso.imageloader.cache.DiskImageCache
import com.riso.imageloader.cache.MemoryImageCache
import com.riso.imageloader.decode.BitmapDecoder
import com.riso.imageloader.impl.DefaultImageLoader
import com.riso.imageloader.network.ImageDownloader
import com.riso.imageloader.util.CacheKeyFactory
import com.riso.imageloader.util.ImageViewTargetGuard
import org.koin.dsl.module

val imageLoaderModule = module {
    single { MemoryImageCache(maxSizeKb = 20 * 1024) } // 20MB approx
    single { DiskImageCache(context = get()) }
    single { CacheInvalidator(memoryCache = get(), diskCache = get()) }
    single { ImageDownloader(okHttpClient = get()) }
    single { BitmapDecoder() }
    single { CacheKeyFactory() }
    single { ImageViewTargetGuard() }

    single<ImageLoader> {
        DefaultImageLoader(
            memoryCache = get(),
            diskCache = get(),
            cacheInvalidator = get(),
            downloader = get(),
            decoder = get(),
            keyFactory = get(),
            targetGuard = get()
        )
    }
}