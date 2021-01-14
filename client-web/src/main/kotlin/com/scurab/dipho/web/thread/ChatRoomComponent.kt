package com.scurab.dipho.web.thread

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.model.ChatItem
import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.util.IDataFormatter
import com.scurab.dipho.common.util.StringTokenizer
import com.scurab.dipho.home.ChatRoomUiState
import com.scurab.dipho.home.ChatRoomViewModel
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
import react.dom.h4
import react.functionalComponent
import react.setState
import styled.css
import styled.styledImg

external interface ChatRoomProps : RProps {
    var chatRoomId: String
}

class RChatRoomState(
    override var isLoading: Boolean,
    override val showLinksExtra: Boolean,
    var chatItems: ChatItems

) : ChatRoomUiState(isLoading, showLinksExtra), RState

class ThreadComponent(props: ChatRoomProps) : BaseRComponent<ChatRoomProps, RChatRoomState>(props) {

    private val dataFormatter by inject<IDataFormatter>()
    private val viewModel by viewModel<ChatRoomViewModel>()

    init {
        state = RChatRoomState(isLoading = false, showLinksExtra = false, chatItems = ChatItems.EMPTY)
    }

    override fun RBuilder.render() {
        div("screen") {
            child(SharedComponents.progressBar(state.isLoading))
            div("screen-header") {
                h4 {
                    +state.chatItems.subject
                }
            }
            div("screen-content") {
                state.chatItems.items.forEachIndexed { index, chatItem ->
                    child(message(index, chatItem))
                }
            }
        }
    }

    override fun componentDidMount() {
        super.componentDidMount()
        with(viewModel) {
            data.observe {
                setState {
                    chatItems = it
                }
            }
            uiState.observe {
                setState {
                    isLoading = it.isLoading
                }
            }
        }
        viewModel.loadData(props.chatRoomId)
    }


    private fun message(index: Int, chatItem: ChatItem) = functionalComponent<RProps>("Message") {
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
            //TODO: extract into components
            if (state.showLinksExtra) {
                val images = chatItem.links.filter {
                    it.endsWith(".png") || it.endsWith(".jpg") || it.endsWith(".jpeg") || it.endsWith(".gif")
                }
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