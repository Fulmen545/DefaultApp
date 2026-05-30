package com.riso.core.dispatcher

import kotlin.coroutines.CoroutineContext

/**
 * Collection of coroutine dispatchers that can be overridden in Unit tests.
 */
interface AppDispatchers {

    @Suppress("PropertyName")
    val Default: CoroutineContext

    @Suppress("PropertyName")
    val Main: CoroutineContext

    @Suppress("PropertyName")
    val IO: CoroutineContext
}
