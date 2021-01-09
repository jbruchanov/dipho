package com.scurab.dipho.home

import com.scurab.dipho.common.arch.BaseCommonViewModel
import com.scurab.dipho.common.coroutines.IDispatchers
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.lifecycle.MutableLifecycleObservable
import com.scurab.dipho.common.model.Author
import com.scurab.dipho.common.model.Thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

open class HomeUiState(open val items: List<Thread>)


class HomeViewModel : BaseCommonViewModel(), KoinComponent {

    private val _uiState = MutableLifecycleObservable(HomeUiState(emptyList()))
    val uiState: LifecycleObservable<HomeUiState> = _uiState

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
        withContext(dispatchers.main) {
            _uiState.item = homeUiState
        }
    }
}