package com.riso.imageloader.cache

object CacheConfig {
    const val TTL_MILLIS: Long = 4 * 60 * 60 * 1000 // 4 hours
    const val DISK_DIR = "image_loader_cache"
}