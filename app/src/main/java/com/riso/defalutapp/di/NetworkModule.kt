package com.riso.defalutapp.di

import com.riso.data.networking.NetworkCallFactory
import com.riso.data.networking.RetrofitNetworkCallFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

fun getNetworkModule(debug: Boolean) = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .baseUrl("https://zipoapps-storage-test.nyc3.digitaloceanspaces.com/")
            .client(get())
            .build()
    }

    single<NetworkCallFactory> {
        RetrofitNetworkCallFactory()
    }

    single {
        val unsaveTrustManager: X509TrustManager = get()
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(unsaveTrustManager), SecureRandom())

        OkHttpClient.Builder()
            .apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                hostnameVerifier { _, _ -> true }
                sslSocketFactory(sslContext.socketFactory, unsaveTrustManager)
                if (debug) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()
    }

    single<X509TrustManager> {
        object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }
}
