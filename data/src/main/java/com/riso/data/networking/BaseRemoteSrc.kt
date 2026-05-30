package com.riso.data.networking

import com.riso.core.Result
import com.riso.core.dispatcher.AppDispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseRemoteSrc(
    protected val networkCallFactory: NetworkCallFactory,
    protected val appDispatchers: AppDispatchers
) {

    /**
     * Create a network call with [AppDispatchers.IO].
     *
     * @param apiCall remote apiCall to execute
     * @return result wrapped in [Result]
     */
    protected suspend fun <T> networkCall(apiCall: suspend () -> T): Result<T> {
        return networkCall(appDispatchers.IO, apiCall)
    }

    /**
     * Create a network call with [coroutineContext].
     *
     * @param apiCall remote apiCall to execute
     * @return result wrapped in [Result]
     */
    protected suspend fun <T> networkCall(
        coroutineContext: CoroutineContext,
        apiCall: suspend () -> T
    ): Result<T> {
        return networkCallFactory.apiCall(coroutineContext, apiCall)
    }
}
