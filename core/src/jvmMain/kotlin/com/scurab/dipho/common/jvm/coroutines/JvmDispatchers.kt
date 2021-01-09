package com.scurab.dipho.common.jvm.coroutines

import com.scurab.dipho.common.coroutines.IDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class JvmDispatchers : IDispatchers {
    override val main: MainCoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineContext = Dispatchers.IO
    override val cpu: CoroutineContext = Dispatchers.Default
}