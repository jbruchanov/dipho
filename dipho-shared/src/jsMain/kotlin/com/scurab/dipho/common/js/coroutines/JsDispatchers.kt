package com.scurab.dipho.common.js.coroutines

import com.scurab.dipho.common.coroutines.IDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class JsDispatchers : IDispatchers {
    override val main: MainCoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineContext = Dispatchers.Default
    override val cpu: CoroutineContext = Dispatchers.Default
}