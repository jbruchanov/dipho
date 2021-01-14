package com.scurab.dipho.home

import com.scurab.dipho.common.IPlatform
import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.ext.showLinksAsButtons
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.common.repo.AppRepo
import com.scurab.dipho.common.usecase.LoadDataUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

open class ThreadUiState(
    open val isLoading: Boolean,
    open val showLinksExtra: Boolean
)

class ThreadViewModel(
    private val repo: AppRepo,
    private val loadDataUseCase: LoadDataUseCase
) : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable<ThreadUiState>()
    val uiState: LifecycleObservable<ThreadUiState> = _uiState

    private val _data = mutableLifecycleObservable<ChatItems>()
    val data: LifecycleObservable<ChatItems> = _data

    private val platform by inject<IPlatform>()

    fun loadData(threadId: String) {
        _uiState.emitItem(uiState(true))
        viewModelScope.launch(dispatchers.io) {
            try {
                loadItems(threadId)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _uiState.emitItem(uiState(false))
            }
        }
    }

    private suspend fun loadItems(threadId: String) {
        repo.getChatRoom(threadId)?.let { _data.emitItem(it) }
        val chatItems = loadDataUseCase.loadChatRoom(threadId)
        withContext(dispatchers.main) {
            _data.emitItem(chatItems)
        }
    }

    private fun uiState(isLoading: Boolean = false) =
        ThreadUiState(isLoading, platform.showLinksAsButtons)
}