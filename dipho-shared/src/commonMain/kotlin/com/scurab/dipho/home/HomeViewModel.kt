package com.scurab.dipho.home

import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.mutableLifecycleObservable
import com.scurab.dipho.common.lifecycle.navigationLifecycleObservable
import com.scurab.dipho.common.model.Author
import com.scurab.dipho.common.model.Thread
import com.scurab.dipho.common.nav.NavigationToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent

open class HomeUiState(open val items: List<Thread>)

class HomeViewModel : BaseCommonViewModel(), KoinComponent {

    private val _uiState = mutableLifecycleObservable(HomeUiState(emptyList()))
    val uiState: LifecycleObservable<HomeUiState> = _uiState
    private val _navigationToken = navigationLifecycleObservable<NavigationToken>()
    val navigationToken: LifecycleObservable<NavigationToken> = _navigationToken

    fun loadItemsAsync() {
        viewModelScope.launch(dispatchers.io) {
            loadItems()
        }
    }

    private suspend fun loadItems() {
        val homeUiState = HomeUiState(
            (0..30).map { Thread("Id:$it", "Subject1:$it", Author("$it", "Author:$it")) }
        )
        delay(1000)
        //TODO: remove main context here
        withContext(dispatchers.main) {
            _uiState.postItem(homeUiState)
        }
    }

    fun onThreadClicked(thread: Thread) {
        _navigationToken.enqueueItem(NavigationToken.Thread(thread.id))
    }
}