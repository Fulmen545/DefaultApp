package com.riso.imageloader.util

import java.security.MessageDigest

class CacheKeyFactory {
    fun fromUrl(url: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
            .digest(url.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}