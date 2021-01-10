package com.scurab.dipho.common.android.arch

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.scurab.dipho.common.android.app.BaseFragment
import com.scurab.dipho.common.android.ext.toCommonLifecycle
import androidx.lifecycle.Lifecycle as AndroidLifecycle

@Suppress("unused")
class AndroidFragmentCommonLifecycleDispatcher(private val fragment: BaseFragment) {

    private val lifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(AndroidLifecycle.Event.ON_ANY)
        fun onAndroidLifecycleEvent(owner: LifecycleOwner, event: AndroidLifecycle.Event) {
            fragment.apply { event.toCommonLifecycle()?.let { setLifecycle(it) } }
        }
    }

    private val viewLifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(AndroidLifecycle.Event.ON_ANY)
        fun onAndroidLifecycleEvent(owner: LifecycleOwner, event: AndroidLifecycle.Event) {
            fragment.apply {
                event.toCommonLifecycle()?.let { setLifecycle(it) }
            }
        }
    }

    /*
        This class is tied to the Fragment instance lifecycle.
        So there is no need for any removeObservable, GC will take care of it
     */
    fun attachObserver() {
        //ignored for now, view's lifecycle is more important
        //fragment.getLifecycle().addObserver(lifecycleObserver)
    }

    fun attachViewObserver() {
        fragment.viewLifecycleOwner.lifecycle.addObserver(viewLifecycleObserver)
    }
}