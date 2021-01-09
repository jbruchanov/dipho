package com.scurab.dipho.common.arch

import com.scurab.dipho.common.core.IHasLogger
import com.scurab.dipho.common.coroutines.IHasDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseCommonViewModel : PlatformViewModel(),
    IHasLogger by IHasLogger.Delegate(),
    IHasDispatchers by IHasDispatchers.Delegate() {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val viewModelScope: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = SupervisorJob() + dispatchers.main.immediate
    }

    /**
     * Internal call for releasing VM stuff.
     * Android calls directly [onCleared] (it's protected), so let's create similar fun we can call
     * without exposing onCleared to public
     */
    @Suppress("FunctionName")
    fun _internalClear() {
        onCleared()
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}