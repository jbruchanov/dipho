package com.scurab.dipho.common.api

import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.model.ChatRooms

interface IServerApi {
    suspend fun getChatRooms(nextPageId: String? = null): ChatRooms
    suspend fun getMessages(chatRoomId: String): ChatItems
}