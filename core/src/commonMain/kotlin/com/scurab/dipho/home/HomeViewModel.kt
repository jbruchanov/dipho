package com.scurab.dipho.home

import com.scurab.dipho.common.core.arch.PlatformViewModel
import com.scurab.dipho.common.core.observable
import com.scurab.dipho.common.model.Author
import com.scurab.dipho.common.model.Thread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class HomeUiState(open val items: List<Thread>)


class HomeViewModel : PlatformViewModel() {

    val uiState = observable(HomeUiState(emptyList()))

    fun loadItemsAsync() {
        GlobalScope.launch {
            loadItems()
        }
    }

    private suspend fun loadItems() {
        delay(1000)
        withContext(Dispatchers.Main) {
            uiState.item = HomeUiState(
                (0..30).map { Thread("Id:$it", "Subject:$it", Author("$it", "Author:$it")) }
            )
        }
    }
}