package com.riso.imageloader.cache

import android.content.Context
import java.io.File

class DiskImageCache(context: Context) {
    private val rootDir = File(context.cacheDir, CacheConfig.DISK_DIR).apply { mkdirs() }

    fun getIfFresh(key: String): File? {
        val file = File(rootDir, key)
        if (!file.exists()) return null
        val age = System.currentTimeMillis() - file.lastModified()
        return if (age <= CacheConfig.TTL_MILLIS) file else null
    }

    fun put(key: String, bytes: ByteArray): File {
        val file = File(rootDir, key)
        file.writeBytes(bytes)
        return file
    }

    fun remove(key: String) {
        File(rootDir, key).delete()
    }

    fun clearAll() {
        rootDir.listFiles()?.forEach { it.delete() }
    }
}