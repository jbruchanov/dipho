package com.scurab.dipho.web.thread

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.model.ChatItem
import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.util.IDataFormatter
import com.scurab.dipho.home.ThreadUiState
import com.scurab.dipho.home.ThreadViewModel
import com.scurab.dipho.web.SharedComponents
import kotlinx.html.classes
import org.koin.core.inject
import react.RBuilder
import react.RProps
import react.RState
import react.child
import react.dom.a
import react.dom.div
import react.dom.img
import react.functionalComponent
import react.setState
import styled.styledDiv

external interface ThreadProps : RProps {
    var threadId: String
}

class RThreadState(
    override var isLoading: Boolean,
    override var chatItems: ChatItems
) : ThreadUiState(isLoading, chatItems), RState

class ThreadComponent(props: ThreadProps) : BaseRComponent<ThreadProps, RThreadState>(props) {

    private val dataFormatter by inject<IDataFormatter>()
    private val viewModel by viewModel<ThreadViewModel>()

    init {
        state = RThreadState(false, ChatItems.EMPTY)
    }

    override fun RBuilder.render() {
        div {
            attrs.classes = setOf("screen")
            child(SharedComponents.progressBar(state.isLoading))
            div {
                +"Subject"
            }
            state.chatItems.items.forEachIndexed { index, chatItem ->
                child(message(index, chatItem))
            }
        }
    }

    override fun componentDidMount() {
        super.componentDidMount()
        with(viewModel) {
            uiState.observe {
                setState {
                    isLoading = it.isLoading
                    chatItems = it.chatItems
                }
            }
        }
        viewModel.loadData(props.threadId)
    }


    private fun RBuilder.message(index: Int, chatItem: ChatItem) = functionalComponent<RProps>("Message") {
        styledDiv {
            attrs.classes = if (index % 2 == 0) Css.messageContentEven else Css.messageContentOdd
            div {
                attrs.classes = Css.messageAuthor
                +chatItem.author.name
            }
            div {
                attrs.classes = Css.messageDate
                +(dataFormatter.toLongTime(chatItem.created) + " " + (dataFormatter.toLongDate(chatItem.created)))
            }
            div {
                attrs.classes = Css.messageText
                +chatItem.text
            }
            if (chatItem.links.isNotEmpty()) {
                div {
                    attrs.classes = setOf("message-links")
                    chatItem.links.forEach {
                        a { attrs.href = it }
                    }
                }
            }
            if (chatItem.images.isNotEmpty()) {
                div {
                    attrs.classes = setOf("message-images")
                    chatItem.images.forEach {
                        img { attrs.src = it }
                    }
                }
            }
        }
    }

    private object Css {
        val messageContentOdd = setOf("message-content", "item-odd")
        val messageContentEven = setOf("message-content", "item-even")
        val messageAuthorDate = setOf("message-authordate")
        val messageAuthor = setOf("message-author")
        val messageDate = setOf("message-date")
        val messageText = setOf("message-text")
    }
}