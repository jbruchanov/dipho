package com.scurab.dipho.home

import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.model.Author
import com.scurab.dipho.common.model.Message
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import kotlin.random.Random

open class ThreadUiState(open val items: List<Message>)

class ThreadViewModel : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable(ThreadUiState(emptyList()))
    val uiState: LifecycleObservable<ThreadUiState> = _uiState

    fun loadData(threadId: String) {
        viewModelScope.launch(dispatchers.io) {
            loadItems(threadId)
        }
    }

    private suspend fun loadItems(threadId: String) {
        val threadUiState = ThreadUiState(
            (0..20).map {
                Message(
                    Author("0", "Name:${if (it % 2 == 0) "A" else "B"}"),
                    "Message:$it",
                    Random.nextLong(),
                    links.getOrNull(it % 10) ?: emptyList(),
                    images.getOrNull(it % 8) ?: emptyList(),
                )
            }
        )
        withContext(dispatchers.main) {
            _uiState.postItem(threadUiState)
        }
    }

    private val links = listOf(
        emptyList(),
        listOf("https://www.google.com"),
        listOf(
            "https://www.youtube.com/watch?v=bydj3ypCLcE&ab_channel=ShepardGaming",
            "https://www.youtube.com/watch?v=IddU0V0J9BY&ab_channel=WowSuchGaming"
        )
    )

    private val images = listOf(
        listOf(
            "https://images.apina.biz/full/77780.jpg",
            "https://images.apina.biz/full/160694.png",
            "https://media.giphy.com/media/OoId38go2peTu/giphy.gif"
        ),
        emptyList(),
        listOf("https://images.apina.biz/full/178191.png")
    )
}