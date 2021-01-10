package com.scurab.dipho.common.android.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.scurab.dipho.common.android.arch.AndroidFragmentCommonLifecycleDispatcher
import com.scurab.dipho.common.android.arch.IFragmentMutatingLifecycleOwner
import com.scurab.dipho.common.core.IHasLogger
import com.scurab.dipho.common.lifecycle.LifecycleObservingHelpers

open class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId),
    IFragmentMutatingLifecycleOwner by IFragmentMutatingLifecycleOwner.Delegate(),
    IHasLogger by IHasLogger.Delegate(),
    LifecycleObservingHelpers {

    constructor() : this(0)

    @Suppress("LeakingThis")
    private val lifecycleDispatcher = AndroidFragmentCommonLifecycleDispatcher(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            //must be done only if we create nonNull view, otherwise it's crashing
            lifecycleDispatcher.attachViewObserver()
        }
    }

    init {
        lifecycleDispatcher.attachObserver()
    }
}