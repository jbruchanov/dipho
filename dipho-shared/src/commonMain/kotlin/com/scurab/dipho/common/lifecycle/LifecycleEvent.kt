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

open class LifecycleObservable<T> protected constructor(
    private val strategy: ILifecycleObserverStrategy<T> = Multicast()
) {
    private val observers = mutableMapOf<Observer<*>, Pair<Observer<T>, ILifecycleOwner>>()
    private var _item: Any? = EMPTY

    protected open fun setItem(item: T) {
        _item = item
        notifyObservers(item)
    }

    protected open fun postItem(item: T) {
        setItem(item)
    }

    protected open fun enqueueItem(item: T) {
        val mainThread = true
        if (mainThread) setItem(item) else postItem(item)
    }

    @Suppress("UNCHECKED_CAST")
    val item: T?
        get() = _item.takeIf { it != EMPTY } as? T

    @Suppress("UNCHECKED_CAST")
    fun requireItem(): T = item ?: throw IllegalStateException("Value not set yet!")

    fun observe(
        lifecycleOwner: ILifecycleOwner,
        observer: Observer<T>
    ) {
        require(strategy.allowedObservers > observers.size) {
            "Max amount ${strategy.allowedObservers} of observers reached"
        }
        val attachedObserver = observers[observer]
        check(attachedObserver == null || attachedObserver.second == lifecycleOwner) {
            "Observer:${observer} already attached with different lifecycleOwner:${lifecycleOwner}"
        }
        observers[observer] = Pair(observer, lifecycleOwner)
        lifecycleOwner.onLifecycleEvent(LifecycleEvent.Resume) {
            //FIXME:
            //observer.invoke(item)
        }
        lifecycleOwner.onLifecycleEvent(LifecycleEvent.Destroy) {
            observers.remove(observer)
        }
    }

    private fun notifyObservers(item: T) {
        //TODO: ensure main thread
        val consume = strategy.notifyObservers(observers, item)
        if (consume) {
            _item = EMPTY
        }
    }

    interface ILifecycleObserverStrategy<T> {
        val allowedObservers: Int
        fun notifyObservers(observers: Map<Observer<*>, Pair<Observer<T>, ILifecycleOwner>>, item: T): Boolean
    }

    companion object {
        private val EMPTY = Any()
    }

    /**
     * Send an item to a resumed observers.
     * Only one observer can be attached! otherwise throws [IllegalStateException]
     */
    class Unicast<T> : ILifecycleObserverStrategy<T> {
        override val allowedObservers: Int = 1

        override fun notifyObservers(
            observers: Map<Observer<*>, Pair<Observer<T>, ILifecycleOwner>>,
            item: T
        ): Boolean {
            val toNotify = observers.filter { it.value.second.lifecycle == LifecycleEvent.Resume }
            check(toNotify.size <= 1) {
                "Bug, Unicast strategy and has >1 observers?!"
            }
            var consume = false
            if (toNotify.isNotEmpty()) {
                toNotify.forEach { entry ->
                    entry.value.first(item)
                }
                consume = true
            }
            return consume
        }
    }

    /**
     * Send an item to all resumed observers.
     * Any number of observers can be attached
     */
    class Multicast<T>(override val allowedObservers: Int = Int.MAX_VALUE) : ILifecycleObserverStrategy<T> {
        override fun notifyObservers(
            observers: Map<Observer<*>, Pair<Observer<T>, ILifecycleOwner>>,
            item: T
        ): Boolean {
            val toNotify = observers.filter { it.value.second.lifecycle == LifecycleEvent.Resume }
            if (toNotify.isNotEmpty()) {
                toNotify.forEach { entry ->
                    entry.value.first(item)
                }
            }
            return false
        }
    }
}

open class MutableLifecycleObservable<T>(strategy: ILifecycleObserverStrategy<T> = Multicast()) :
    LifecycleObservable<T>(strategy) {

    constructor(item: T, strategy: ILifecycleObserverStrategy<T>) : this(strategy) {
        setItem(item)
    }

    public override fun setItem(item: T) = super.setItem(item)
    public override fun postItem(item: T) = super.postItem(item)
    public override fun enqueueItem(item: T) = super.enqueueItem(item)
}

fun <T> mutableLifecycleObservable() = MutableLifecycleObservable<T>(LifecycleObservable.Multicast())
fun <T> mutableLifecycleObservable(item: T) = MutableLifecycleObservable(item, LifecycleObservable.Multicast())
fun <T> navigationLifecycleObservable() = MutableLifecycleObservable<T>(LifecycleObservable.Unicast())