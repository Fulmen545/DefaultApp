package com.riso.imageloader.impl

import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.riso.imageloader.api.ImageLoader
import com.riso.imageloader.cache.CacheInvalidator
import com.riso.imageloader.cache.DiskImageCache
import com.riso.imageloader.cache.MemoryImageCache
import com.riso.imageloader.decode.BitmapDecoder
import com.riso.imageloader.network.ImageDownloader
import com.riso.imageloader.util.CacheKeyFactory
import com.riso.imageloader.util.ImageViewTargetGuard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultImageLoader(
    private val memoryCache: MemoryImageCache,
    private val diskCache: DiskImageCache,
    private val cacheInvalidator: CacheInvalidator,
    private val downloader: ImageDownloader,
    private val decoder: BitmapDecoder,
    private val keyFactory: CacheKeyFactory,
    private val targetGuard: ImageViewTargetGuard
) : ImageLoader {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun load(url: String, @DrawableRes placeholderRes: Int, target: ImageView) {
        val key = keyFactory.fromUrl(url)
        targetGuard.mark(target, key)
        target.setImageResource(placeholderRes)

        scope.launch {
            try {
                val bitmap = resolveBitmap(url, key)
                if (targetGuard.isStillValid(target, key)) {
                    target.setImageBitmap(bitmap)
                }
            } catch (t: Throwable) {
                // Placeholder ostava, appka nespadne
                Log.w("ImageLoader", "Image load failed for $url: ${t.message}", t)
            }
        }
    }

    override suspend fun preload(url: String) {
        val key = keyFactory.fromUrl(url)
        resolveBitmap(url, key)
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) { cacheInvalidator.clearAll() }
    }

    private suspend fun resolveBitmap(url: String, key: String) = withContext(Dispatchers.IO) {
        memoryCache.get(key)?.let { return@withContext it }

        diskCache.getIfFresh(key)?.let { file ->
            val bmp = decoder.fromFile(file)
            memoryCache.put(key, bmp)
            return@withContext bmp
        }

        val bytes = downloader.download(url)
        diskCache.put(key, bytes)
        val bmp = decoder.fromBytes(bytes)
        memoryCache.put(key, bmp)
        bmp
    }
}