package com.scurab.dipho.web.main

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.model.Thread
import com.scurab.dipho.home.HomeUiState
import com.scurab.dipho.home.HomeViewModel
import kotlinx.css.padding
import kotlinx.css.pt
import react.RBuilder
import react.RProps
import react.RState
import react.dom.div
import react.setState
import styled.css
import styled.styledDiv

class RHomeState(override var items: List<Thread>) : HomeUiState(items), RState

class HomeComponent(props: RProps) : BaseRComponent<RProps, RHomeState>(props) {

    private val viewModel by viewModel<HomeViewModel>()

    init {
        state = RHomeState(emptyList())
    }

    override fun componentDidMount() {
        super.componentDidMount()
        viewModel.uiState.observe {
            logger.d("HomeComponent", "Update UI")
            setState {
                items = it.items
            }
        }
        viewModel.loadItemsAsync()
    }

    override fun RBuilder.render() {
        div {
            +"MainPage"
        }
        div {
            state.items.forEach {
                styledDiv {
                    css {
                        padding(10.pt)
                    }
                    +it.subject
                }
            }
        }
    }
}