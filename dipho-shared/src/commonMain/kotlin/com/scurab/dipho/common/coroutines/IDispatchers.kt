package com.scurab.dipho.common.coroutines

import kotlinx.coroutines.MainCoroutineDispatcher
import org.koin.core.context.KoinContextHandler
import kotlin.coroutines.CoroutineContext

/**
 * Common iface to have a holder of coroutine dispatchers instead of static class
 *
 */
interface IDispatchers {
    /**
     * Main Thread dispatcher
     */
    val main: MainCoroutineDispatcher

    /**
     * Equivalent of [kotlinx.coroutines.Dispatchers.IO]
     */
    val io: CoroutineContext

    /**
     * Equivalent of [kotlinx.coroutines.Dispatchers.Default]
     * Backed by thread pool with size equal to the number of CPU cores, but is at least two.
     */
    val cpu: CoroutineContext
}

interface IHasDispatchers {
    val dispatchers : IDispatchers

    class Delegate : IHasDispatchers {
        override val dispatchers: IDispatchers by KoinContextHandler.get().inject()
    }
}