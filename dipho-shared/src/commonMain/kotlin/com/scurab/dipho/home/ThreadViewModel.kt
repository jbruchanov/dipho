package com.scurab.dipho.home

import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.model.ChatItems
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

open class ThreadUiState(
    open val isLoading: Boolean,
    open val chatItems: ChatItems
)

class ThreadViewModel : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable<ThreadUiState>()
    val uiState: LifecycleObservable<ThreadUiState> = _uiState

    private val api by inject<IServerApi>()

    fun loadData(threadId: String) {
        _uiState.emitItem(ThreadUiState(true, uiState.item?.chatItems ?: ChatItems.EMPTY))
        viewModelScope.launch(dispatchers.io) {
            try {
                loadItems(threadId)
            } catch (e: Exception) {
                _uiState.emitItem(ThreadUiState(false, uiState.item?.chatItems ?: ChatItems.EMPTY))
            }
        }
    }

    private suspend fun loadItems(threadId: String) {
        val chatItems = api.getMessages(threadId)
        withContext(dispatchers.main) {
            _uiState.postItem(ThreadUiState(false, chatItems))
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