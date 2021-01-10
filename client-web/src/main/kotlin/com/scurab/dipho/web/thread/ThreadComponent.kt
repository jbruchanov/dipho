package com.scurab.dipho.web.thread

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.model.ChatItem
import com.scurab.dipho.home.ThreadUiState
import com.scurab.dipho.home.ThreadViewModel
import kotlinx.css.padding
import kotlinx.css.px
import kotlinx.html.classes
import react.RBuilder
import react.RProps
import react.RState
import react.child
import react.dom.a
import react.dom.div
import react.dom.hr
import react.dom.img
import react.functionalComponent
import react.setState
import styled.css
import styled.styledDiv

external interface ThreadProps : RProps {
    var threadId: String
}

class RThreadState(override var items: List<ChatItem>) : ThreadUiState(items), RState

class ThreadComponent(props: ThreadProps) : BaseRComponent<ThreadProps, RThreadState>(props) {

    private val viewModel by viewModel<ThreadViewModel>()

    init {
        state = RThreadState(emptyList())
    }

    override fun RBuilder.render() {
        div {
            div {
                +"Subject"
            }
            state.items.forEach {
                child(message(it))
            }
        }
    }

    override fun componentDidMount() {
        super.componentDidMount()
        with(viewModel) {
            uiState.observe {
                setState {
                    items = it.items
                }
            }
        }
        viewModel.loadData(props.threadId)
    }


    private fun RBuilder.message(chatItem: ChatItem) = functionalComponent<RProps>("Message") {
        styledDiv {
            css {
                padding(10.px)
            }
            attrs.classes = setOf("thread-content")
            div {
                attrs.classes = setOf("thread-author")
                +"Autor: ${chatItem.author.name}"
            }
            attrs.classes = setOf("thread-content")
            div {
                attrs.classes = setOf("thread-author")
                +chatItem.created.toString()
            }
            div {
                attrs.classes = setOf("thread-text")
                +chatItem.text
            }
            if (chatItem.links.isNotEmpty()) {
                div {
                    attrs.classes = setOf("thread-links")
                    chatItem.links.forEach {
                        a { attrs.href = it }
                    }
                }
            }
            if (chatItem.images.isNotEmpty()) {
                div {
                    attrs.classes = setOf("thread-images")
                    chatItem.images.forEach {
                        img { attrs.src = it }
                    }
                }
            }
            hr {  }
        }
    }
}