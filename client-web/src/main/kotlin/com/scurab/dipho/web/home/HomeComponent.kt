package com.scurab.dipho.web.home

import com.scurab.dipho.common.js.arch.BaseRComponent
import com.scurab.dipho.common.js.arch.viewModel
import com.scurab.dipho.common.js.nav.bind
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.common.util.IDataFormatter
import com.scurab.dipho.home.HomeUiState
import com.scurab.dipho.home.HomeViewModel
import com.scurab.dipho.web.SharedComponents
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import org.koin.core.inject
import react.RBuilder
import react.RProps
import react.RState
import react.child
import react.dom.br
import react.dom.div
import react.dom.h4
import react.dom.span
import react.functionalComponent
import react.setState

class RHomeState(
    override var isLoading: Boolean,
    override var items: List<ChatRoom>
) : HomeUiState(isLoading, items), RState

class HomeComponent(props: RProps) : BaseRComponent<RProps, RHomeState>(props) {

    private val dataFormatter by inject<IDataFormatter>()
    private val viewModel by viewModel<HomeViewModel>()
    private val threadClickHandler = { chatRoom: ChatRoom -> viewModel.onThreadClicked(chatRoom) }

    init {
        state = RHomeState(true, emptyList())
    }

    override fun componentDidMount() {
        super.componentDidMount()
        with(viewModel) {
            viewModel.navigationToken.bind(this@HomeComponent)
            uiState.observe {
                logger.d("HomeComponent", "Update UI")
                setState {
                    isLoading = it.isLoading
                    items = it.items
                }
            }
            loadItemsAsync()
        }
    }

    override fun RBuilder.render() {
        div {
            attrs.classes = setOf("screen")
            child(SharedComponents.progressBar(state.isLoading))
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
                attrs.classes = if (index % 2 == 0) Classes.threadEven else Classes.threadOdd
                div {
                    attrs.classes = Classes.threadCol1
                    div {
                        attrs.classes = Classes.author
                        +item.author.name
                    }
                    div {
                        attrs.classes = Classes.subject
                        +item.subject
                    }
                }
                div {
                    attrs.classes = Classes.msgCount
                    span { +item.messages.toString() }
                }
                div {
                    attrs.classes = Classes.created
                    span {
                        +dataFormatter.toLongDate(item.created)
                        br { }
                        +dataFormatter.toLongTime(item.created)
                    }
                }
            }
        })

    companion object {
        private object Classes {
            val threadEven = setOf("thread", "item-even")
            val threadOdd = setOf("thread", "item-odd")
            val threadCol1 = setOf("thread-col1")
            val author = setOf("author")
            val subject = setOf("subject")
            val msgCount = setOf("msg-count")
            val created = setOf("created")
        }
    }
}
