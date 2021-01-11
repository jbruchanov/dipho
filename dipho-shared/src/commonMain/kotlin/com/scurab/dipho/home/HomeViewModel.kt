package com.scurab.dipho.home

import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.lifecycle.navigationLifecycleObservable
import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.common.nav.NavigationToken
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

open class HomeUiState(
    open val isLoading: Boolean,
    open val items: List<ChatRoom>
)

class HomeViewModel : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable(HomeUiState(true, emptyList()))
    val uiState: LifecycleObservable<HomeUiState> = _uiState
    private val _navigationToken = navigationLifecycleObservable<NavigationToken>()
    val navigationToken: LifecycleObservable<NavigationToken> = _navigationToken

    private val api by inject<IServerApi>()

    fun loadItemsAsync() {
        _uiState.emitItem(HomeUiState(true, _uiState.item?.items ?: emptyList()))
        viewModelScope.launch(dispatchers.io) {
            try {
                loadItems()
            } catch (e: Exception) {
                _uiState.emitItem(HomeUiState(false, _uiState.item?.items ?: emptyList()))
            }
        }
    }

    private suspend fun loadItems() {
        val data = api.getChatRooms()
        withContext(dispatchers.main) {
            _uiState.postItem(HomeUiState(false, data.items))
        }
    }

    fun onThreadClicked(chatRoom: ChatRoom) {
        _navigationToken.emitItem(NavigationToken.Thread(chatRoom.id))
    }
}