package com.scurab.dipho.web.home

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.js.nav.bind
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.home.HomeUiState
import com.scurab.dipho.home.HomeViewModel
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.RState
import react.child
import react.dom.div
import react.dom.h4
import react.functionalComponent
import react.setState

class RHomeState(override var items: List<ChatRoom>) : HomeUiState(items), RState

class HomeComponent(props: RProps) : BaseRComponent<RProps, RHomeState>(props) {

    private val viewModel by viewModel<HomeViewModel>()
    private val threadClickHandler = { chatRoom: ChatRoom -> viewModel.onThreadClicked(chatRoom) }

    init {
        state = RHomeState(emptyList())
    }

    override fun componentDidMount() {
        super.componentDidMount()
        with(viewModel) {
            viewModel.navigationToken.bind(this@HomeComponent)
            uiState.observe {
                logger.d("HomeComponent", "Update UI")
                setState {
                    items = it.items
                }
            }
            loadItemsAsync()
        }
    }

    override fun RBuilder.render() {
        div {
            attrs.classes = setOf("screen")
            child(header)
            div {
                attrs.classes = setOf("screen-content")
                state.items.forEachIndexed { index, thread ->
                    thread(index, thread, threadClickHandler)
                }
            }
            child(footer)
        }
    }

    private val header = functionalComponent<RProps> {
        div {
            attrs.classes = setOf("home-header", "screen-header")
            h4 { +"Header" }
        }
    }

    private val footer = functionalComponent<RProps> {
        div {
            attrs.classes = setOf("home-footer", "screen-footer")
            h4 { +"Footer" }
        }
    }

    fun RBuilder.thread(index: Int, item: ChatRoom, clickHandler: (ChatRoom) -> Unit) =
        child(functionalComponent("Thread") {
            div {
                attrs.onClickFunction = { clickHandler(item) }
                attrs.classes = setOf("thread", if (index % 2 == 0) "thread-even" else "thread-odd")
                div { +item.author.name }
                div { +item.subject }
            }
        })
}
