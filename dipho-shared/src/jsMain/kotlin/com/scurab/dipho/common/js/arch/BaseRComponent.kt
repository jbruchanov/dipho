package com.scurab.dipho.common.js.arch

import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.core.IHasLogger
import com.scurab.dipho.common.lifecycle.ILifecycleOwner
import com.scurab.dipho.common.lifecycle.LifecycleEvent
import com.scurab.dipho.common.lifecycle.LifecycleObservingHelpers
import com.scurab.dipho.common.lifecycle.LifecycleRegistry
import kotlinext.js.jsObject
import org.koin.core.KoinComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import react.RComponent
import react.RProps
import react.RState

interface IRComponentMutatingLifecycleOwner : ILifecycleOwner {
    fun BaseRComponent<*, *>.setLifecycle(lifecycle: LifecycleEvent)

    class Delegate : LifecycleRegistry(), IRComponentMutatingLifecycleOwner {
        override fun BaseRComponent<*, *>.setLifecycle(lifecycle: LifecycleEvent) {
            super.setLifecycleState(this, lifecycle)
        }
    }
}

abstract class BaseRComponent<P : RProps, S : RState>(props: P) : RComponent<P, S>(props),
    IRComponentMutatingLifecycleOwner by IRComponentMutatingLifecycleOwner.Delegate(),
    IHasLogger by IHasLogger.Delegate(),
    LifecycleObservingHelpers,
    KoinComponent {

    constructor() : this(jsObject { })

    override fun componentDidMount() {
        //super.componentDidMount()
        setLifecycle(LifecycleEvent.Resume)
    }

    override fun componentWillUnmount() {
        //super.componentWillUnmount()
        setLifecycle(LifecycleEvent.Destroy)
    }
}

inline fun <reified T : BaseCommonViewModel> BaseRComponent<*, *>.viewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy(LazyThreadSafetyMode.NONE) {
    getKoin().get<T>(qualifier, parameters).also { vm ->
        onLifecycleEvent(LifecycleEvent.Destroy) {
            vm._internalClear()
        }
    }
}