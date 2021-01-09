package com.scurab.dipho.common.lifecycle

import com.scurab.dipho.common.ext.sign

typealias ILifecycleObserver = (LifecycleEventToken) -> Unit

interface ILifecycleOwner {
    val lifecycle: ILifecycleEvent
    fun <T : ILifecycleEvent> onLifecycleEvent(lifecycle: T, block: ILifecycleObserver)
    //remove observer ?
}

data class LifecycleEventToken(
    val lifecycle: ILifecycleEvent,
    val owner: ILifecycleOwner
)

open class LifecycleRegistry : ILifecycleOwner {
    private var _lifecycle: ILifecycleEvent = LifecycleEvent.Init
    private val observers = mutableListOf<LifecycleRegistryItem>()

    protected fun setLifecycleState(registryOwner: ILifecycleOwner, lifecycle: LifecycleEvent) {
        if (_lifecycle == lifecycle) return
        var actualIndex = LifecycleEvent.States.indexOf(_lifecycle)
        val targetIndex = LifecycleEvent.States.indexOf(lifecycle)
        val step = (targetIndex > actualIndex).sign
        while (actualIndex != targetIndex) {
            actualIndex += step
            _lifecycle = LifecycleEvent.States[actualIndex]
            dispatchEvent(registryOwner, _lifecycle)
        }

        if (lifecycle == LifecycleEvent.Destroy) {
            observers.clear()
        }
    }

    override val lifecycle: ILifecycleEvent get() = _lifecycle

    private fun dispatchEvent(registryOwner: ILifecycleOwner, lifecycle: ILifecycleEvent) {
        observers.forEach { it.dispatch(registryOwner, lifecycle) }
    }

    override fun <T : ILifecycleEvent> onLifecycleEvent(lifecycle: T, block: ILifecycleObserver) {
        @Suppress("UNCHECKED_CAST")
        observers.add(LifecycleRegistryItem(lifecycle, block))
    }

    private class LifecycleRegistryItem(
        private val lifecycle: ILifecycleEvent,
        private val observer: ILifecycleObserver
    ) {
        fun dispatch(registryOwner: ILifecycleOwner, lifecycle: ILifecycleEvent) {

            if (this.lifecycle == lifecycle || this.lifecycle == ILifecycleEvent.Any) {
                observer.invoke(LifecycleEventToken(lifecycle, registryOwner))
            }
        }
    }
}