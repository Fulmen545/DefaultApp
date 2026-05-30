package com.riso.data.networking

import android.util.Log
import com.riso.core.Result
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

/**
 * Factory responsible for creating platform specific network call wrappers.
 */
interface NetworkCallFactory {

    /**
     * Create an api call.
     *
     * @param coroutineContext context in which to run the call
     * @param apiCall api call to execute
     * @return result wrapped in [Result]
     */
    suspend fun <T> apiCall(
        coroutineContext: CoroutineContext,
        apiCall: suspend () -> T
    ): Result<T>
}

/**
 * Factory responsible for creating platform specific network call wrappers with retrofit.
 */
class RetrofitNetworkCallFactory() : NetworkCallFactory {

    override suspend fun <T> apiCall(
        coroutineContext: CoroutineContext,
        apiCall: suspend () -> T
    ): Result<T> = withContext(coroutineContext) {
        try {
            val result = apiCall.invoke()
            Result.Success(result)
        } catch (throwable: Throwable) {
            Log.e("NetworkCall", "API failed: ${throwable.javaClass.name}", throwable)
            when (throwable) {
                is IOException -> Result.connectionFailure(throwable)
                is HttpException -> Result.httpFailure(throwable.code(), throwable)
                else -> Result.connectionFailure(throwable)
            }
        }
    }
}
