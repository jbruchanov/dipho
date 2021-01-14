package com.scurab.dipho.web.thread

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.model.ChatItem
import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.util.IDataFormatter
import com.scurab.dipho.common.util.StringTokenizer
import com.scurab.dipho.home.ThreadUiState
import com.scurab.dipho.home.ThreadViewModel
import com.scurab.dipho.web.SharedComponents
import com.scurab.dipho.web.ext.aTab
import kotlinx.css.maxWidth
import kotlinx.css.px
import org.koin.core.inject
import react.RBuilder
import react.RProps
import react.RState
import react.child
import react.dom.br
import react.dom.div
import react.functionalComponent
import react.setState
import styled.css
import styled.styledImg

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
        div(classes = "screen") {
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
        div(if (index % 2 == 0) Css.messageContentEven else Css.messageContentOdd) {
            div(Css.messageAuthor) {
                +chatItem.author.name
            }
            div(Css.messageDate) {
                +(dataFormatter.toLongTime(chatItem.created) + " " + (dataFormatter.toLongDate(chatItem.created)))
            }
            div(Css.messageText) {
                processChatItem(chatItem)
            }

            val images = chatItem.links.filter {
                it.endsWith(".png") || it.endsWith(".jpg") || it.endsWith(".jpeg") || it.endsWith(".gif")
            }

            return@div

            val links = chatItem.links - images
            if (links.isNotEmpty()) {
                div(Css.messageLinks) {
                    chatItem.links.forEach {
                        div {
                            aTab(it) {
                                +it
                            }
                        }
                    }
                }
            }

            if (images.isNotEmpty()) {
                div(Css.messageImages) {
                    images.forEach {
                        div {
                            aTab(it) {
                                styledImg {
                                    css { maxWidth = 300.px }
                                    attrs.src = it
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private val newLineList = listOf("\n")

    private fun RBuilder.processChatItem(chatItem: ChatItem) {
        StringTokenizer(chatItem.text, chatItem.links + newLineList).forEach { token ->
            when (token) {
                is StringTokenizer.Token.Text -> +token.text
                is StringTokenizer.Token.Item -> {
                    val text = token.text
                    when {
                        text == "\n" -> br { }
                        text.startsWith("http") -> aTab(text, classes = Css.messageLink) { +text }
                    }
                }
            }
        }
    }

    private object Css {
        val messageContentOdd = "message-content item-odd"
        val messageContentEven = "message-content item-even"
        val messageAuthorDate = "message-authordate"
        val messageAuthor = "message-author"
        val messageDate = "message-date"
        val messageText = "message-text"
        val messageLink = "message-link"
        val messageLinks = "message-links"
        val messageImages = "message-images"
    }
}