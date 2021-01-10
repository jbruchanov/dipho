package com.scurab.dipho.common.android.ext

import androidx.lifecycle.Lifecycle as AndroidLifecycle
import com.scurab.dipho.common.lifecycle.LifecycleEvent as CommonLifecycle

/**
 * Convert [androidx.lifecycle.Lifecycle.Event] to [com.scurab.dipho.common.core.arch.lifecycle.LifecycleEvent]
 * @return a value or null if there is no common equivalent
 */
fun AndroidLifecycle.Event.toCommonLifecycle(): CommonLifecycle? {
    return when (this) {
        AndroidLifecycle.Event.ON_CREATE -> CommonLifecycle.Init
        AndroidLifecycle.Event.ON_START -> null
        AndroidLifecycle.Event.ON_RESUME -> CommonLifecycle.Resume
        AndroidLifecycle.Event.ON_PAUSE -> CommonLifecycle.Pause
        AndroidLifecycle.Event.ON_STOP -> null
        AndroidLifecycle.Event.ON_DESTROY -> CommonLifecycle.Destroy
        else -> throw UnsupportedOperationException("$this")
    }
}