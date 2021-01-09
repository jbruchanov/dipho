package com.scurab.dipho.common.android.arch

import com.scurab.dipho.common.android.app.BaseFragment
import com.scurab.dipho.common.lifecycle.ILifecycleOwner
import com.scurab.dipho.common.lifecycle.LifecycleEvent
import com.scurab.dipho.common.lifecycle.LifecycleRegistry

interface IFragmentMutatingLifecycleOwner : ILifecycleOwner {
    fun BaseFragment.setLifecycle(lifecycle: LifecycleEvent)

    class Delegate : LifecycleRegistry(), IFragmentMutatingLifecycleOwner {
        override fun BaseFragment.setLifecycle(lifecycle: LifecycleEvent) {
            super.setLifecycleState(this, lifecycle)
        }
    }
}