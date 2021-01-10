package com.scurab.dipho.web.home

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.model.Thread
import com.scurab.dipho.home.HomeUiState
import com.scurab.dipho.home.HomeViewModel
import kotlinx.css.h4
import kotlinx.css.padding
import kotlinx.css.pt
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.RState
import react.ReactElement
import react.dom.button
import react.dom.div
import react.dom.h1
import react.dom.h4
import react.setState
import styled.css
import styled.styledDiv
import kotlin.js.Date

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

    fun RBuilder.header(state: RHomeState) {
        div {
            attrs.classes = setOf("screen-header")
            h4 { +"Header" }
        }
    }

    fun RBuilder.footer(buttonHandler: () -> Unit) {
        div {
            attrs.classes = setOf("screen-footer")
            h4 { +"Footer" }
        }
    }

    fun RBuilder.thread(item: Thread, index: Int, clickHandler: (Thread) -> Unit): ReactElement {
        return div {
            attrs.onClickFunction = { clickHandler(item) }
            attrs.classes = setOf("thread", if (index % 2 == 0) "thread-even" else "thread-odd")
            div { +item.author.name }
            div { +item.subject }
        }
    }
}