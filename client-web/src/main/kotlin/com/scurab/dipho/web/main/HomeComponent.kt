package com.scurab.dipho.web.main

import com.scurab.dipho.common.model.Thread
import com.scurab.dipho.home.HomeUiState
import com.scurab.dipho.home.HomeViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.setState

class RHomeState(override var items: List<Thread>) : HomeUiState(items), RState

class HomeComponent(props: RProps) : RComponent<RProps, RHomeState>(props), KoinComponent {

    private val viewModel by inject<HomeViewModel>()

    init {
        state = RHomeState(emptyList())
    }

    override fun componentDidMount() {
        viewModel.uiState.observe {
            setState {
                items = it.items
            }
        }
        GlobalScope.launch {
            viewModel.loadItems()
        }
    }

    override fun RBuilder.render() {
        div {
            +"MainPage"
        }
        div {
            state.items.forEach {
                div { +it.subject }
            }
        }
    }
}