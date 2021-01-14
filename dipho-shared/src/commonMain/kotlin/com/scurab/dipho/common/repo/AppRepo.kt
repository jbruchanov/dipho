package com.scurab.dipho.common.repo

import com.scurab.dipho.common.model.ChatItems
import com.scurab.dipho.common.model.ChatRoom
import com.scurab.dipho.common.model.ChatRooms

class AppRepo {

    private val chatsPerId = mutableMapOf<String, ChatRoom>()
    private val chatRoomsPerId = mutableMapOf<String, ChatItems>()

    fun getChatRooms(): Map<String, ChatRoom> = chatsPerId

    fun getChatRoom(id: String) = chatRoomsPerId[id]

    fun updateChatRooms(item: ChatRooms) {
        chatsPerId.putAll(item.items.associateBy { it.id })
    }

    fun updateChatRoomItems(item: ChatItems) {
        chatRoomsPerId[item.roomId] = item
    }
}