package com.scurab.dipho.common.lifecycle

interface ILifecycleEvent {
    object Any : ILifecycleEvent
}

sealed class LifecycleEvent : ILifecycleEvent {
    object Init : LifecycleEvent()
    object Resume : LifecycleEvent()
    object Pause : LifecycleEvent()
    object Destroy : LifecycleEvent()

    companion object {
        //important to keep it in order
        val States by lazy {
            listOf(Init, Resume, Pause, Destroy)
        }
    }
}

interface LifecycleObservingHelpers : ILifecycleOwner {
    fun <T> LifecycleObservable<T>.observe(block: (T) -> Unit) = this.observe(this@LifecycleObservingHelpers, block)
}

typealias Observer<T> = (T) -> Unit

open class LifecycleObservable<T>(item: T) {

    private val observers = mutableMapOf<Observer<*>, Pair<Observer<T>, ILifecycleOwner>>()

    open var item: T = item
        protected set(value) {
            field = value
            notifyObservers(value)
        }

    fun observe(
        lifecycleOwner: ILifecycleOwner,
        observer: Observer<T>
    ) {
        val attachedObserver = observers[observer]
        check(attachedObserver == null || attachedObserver.second == lifecycleOwner) {
            "Observer:${observer} already attached with different lifecycleOwner:${lifecycleOwner}"
        }
        observers[observer] = Pair(observer, lifecycleOwner)
        lifecycleOwner.onLifecycleEvent(LifecycleEvent.Resume) {
            observer.invoke(item)
        }
        lifecycleOwner.onLifecycleEvent(LifecycleEvent.Destroy) {
            observers.remove(observer)
        }
    }

    private fun notifyObservers(item: T) {
        val toNotify = observers.filter { it.value.second.lifecycle == LifecycleEvent.Resume }
        if (toNotify.isNotEmpty()) {
            //TODO: main thread
            toNotify.forEach { entry ->
                entry.value.first(item)
            }
        }
    }
}

open class MutableLifecycleObservable<T>(item: T) : LifecycleObservable<T>(item) {
    public override var item: T
        get() = super.item
        set(value) {
            super.item = value
        }
}