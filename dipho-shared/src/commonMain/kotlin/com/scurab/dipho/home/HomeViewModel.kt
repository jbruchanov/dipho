package com.scurab.dipho.home

import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.lifecycle.navigationLifecycleObservable
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.common.model.ChatRooms
import com.scurab.dipho.common.nav.NavigationToken
import com.scurab.dipho.common.repo.AppRepo
import com.scurab.dipho.common.usecase.LoadDataUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

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

    private val _data = mutableLifecycleObservable<Collection<ChatRoom>>()
    val data: LifecycleObservable<Collection<ChatRoom>> = _data

    val api by inject<IServerApi>()

    fun loadItemsAsync() {
        _uiState.emitItem(HomeUiState(true))
        viewModelScope.launch(dispatchers.io) {
            try {
                loadItems()
            } catch (e: Exception) {
                e.printStackTrace()
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