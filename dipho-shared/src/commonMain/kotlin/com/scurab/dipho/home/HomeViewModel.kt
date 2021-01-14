package com.scurab.dipho.home

import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.lifecycle.navigationLifecycleObservable
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.common.nav.NavigationToken
import com.scurab.dipho.common.repo.AppRepo
import com.scurab.dipho.common.usecase.LoadDataUseCase
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

open class HomeUiState(
    open val isLoading: Boolean,
)

class HomeViewModel(
    private val repo: AppRepo,
    private val loadDataUseCase: LoadDataUseCase
) : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable(HomeUiState(true))
    val uiState: LifecycleObservable<HomeUiState> = _uiState
    private val _navigationToken = navigationLifecycleObservable<NavigationToken>()
    val navigationToken: LifecycleObservable<NavigationToken> = _navigationToken

    private val _data = mutableLifecycleObservable<Collection<ChatRoom>>(emptyList())
    val data: LifecycleObservable<Collection<ChatRoom>> = _data

    fun loadItemsAsync() {
        _uiState.emitItem(HomeUiState(true))
        viewModelScope.launch(dispatchers.io) {
            try {
                loadItems()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _uiState.emitItem(HomeUiState(false))
            }
        }
    }

    private suspend fun loadItems() {
        _data.emitItem(repo.getChatRooms().values)
        _data.emitItem(loadDataUseCase.loadChatRooms().items)
    }

    fun onThreadClicked(chatRoom: ChatRoom) {
        _navigationToken.emitItem(NavigationToken.ChatRoom(chatRoom.subject, chatRoom.id))
    }
}