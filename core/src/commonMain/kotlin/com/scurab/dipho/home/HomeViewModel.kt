package com.scurab.dipho.home

import com.scurab.dipho.common.core.observable
import com.scurab.dipho.common.model.Author
import com.scurab.dipho.common.model.Thread
import kotlinx.coroutines.delay

open class HomeUiState(open val items: List<Thread>)

class HomeViewModel {

    val uiState = observable(HomeUiState(emptyList()))

    suspend fun loadItems() {
        delay(1000)
        uiState.item = HomeUiState(
            (0..10).map { Thread("Id:$it", "Subject:$it", Author("$it", "Author:$it")) }
        )
    }
}