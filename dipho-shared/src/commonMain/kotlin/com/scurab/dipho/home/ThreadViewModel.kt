package com.scurab.dipho.home

import com.scurab.dipho.common.IPlatform
import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.ext.showLinksAsButtons
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.model.ChatItems
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

open class ThreadUiState(
    open val isLoading: Boolean,
    open val chatItems: ChatItems,
    open val showLinksExtra: Boolean
)

class ThreadViewModel : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable<ThreadUiState>()
    val uiState: LifecycleObservable<ThreadUiState> = _uiState

    private val api by inject<IServerApi>()
    private val platform by inject<IPlatform>()

    fun loadData(threadId: String) {
        _uiState.emitItem(uiState(uiState.item?.chatItems, true))
        viewModelScope.launch(dispatchers.io) {
            try {
                loadItems(threadId)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.emitItem(
                    uiState(uiState.item?.chatItems)
                )
            }
        }
    }

    private suspend fun loadItems(threadId: String) {
        val chatItems = api.getMessages(threadId)
        withContext(dispatchers.main) {
            _uiState.postItem(uiState(chatItems))
        }
    }

    private fun uiState(chatItems: ChatItems?, isLoading: Boolean = false) =
        ThreadUiState(isLoading, chatItems ?: ChatItems.EMPTY, platform.showLinksAsButtons)
}