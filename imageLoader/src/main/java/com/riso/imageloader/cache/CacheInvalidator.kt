package com.riso.imageloader.cache

class CacheInvalidator(
    private val memoryCache: MemoryImageCache,
    private val diskCache: DiskImageCache
) {
    fun invalidate(key: String) {
        memoryCache.remove(key)
        diskCache.remove(key)
    }

    fun clearAll() {
        memoryCache.clear()
        diskCache.clearAll()
    }
}
