package com.riso.imageloader.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ImageDownloader(private val okHttpClient: OkHttpClient) {

    suspend fun download(url: String): ByteArray = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(url)
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw ImageDownloadException.Http(response.code, url)
            }
            response.body.bytes()
        }
    }
}

sealed class ImageDownloadException(message: String): IOException(message) {
    class Http(val code: Int, val url: String): ImageDownloadException("HTTP $code for $url")
}