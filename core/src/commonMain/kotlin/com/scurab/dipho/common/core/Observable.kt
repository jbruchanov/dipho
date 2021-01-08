package com.scurab.dipho.common.core

import kotlin.properties.Delegates

typealias Observer<T> = (T) -> Unit

fun <T> observable(item: T) = Observable(item)

class Observable<T>(item: T) {
    private val observers = mutableSetOf<Observer<T>>()

    var item: T by Delegates.observable(item) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            notifyChange()
        }
    }

    fun addObserver(observer: Observer<T>) {
        observers.add(observer)
        notifyChange()
    }

    fun removeObserver(observer: Observer<T>) = observers.remove(observer)

    fun observe(observer: Observer<T>) = addObserver(observer)

    fun notifyChange() {
        observers.forEach { it(item) }
    }

    inline fun updating(block: T.() -> Unit) {
        block(item)
        notifyChange()
    }
}