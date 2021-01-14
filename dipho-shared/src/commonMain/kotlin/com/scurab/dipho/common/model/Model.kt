package com.scurab.dipho.common.model

import kotlinx.serialization.Serializable

@Serializable
class Author(
    val id: String,
    val name: String
)

@Serializable
class ChatRooms(
    val nextPageId: String,
    val items: List<ChatRoom>
)

@Serializable
class ChatRoom(
    val id: String,
    val subject: String,
    val author: Author,
    val messages: Int,
    val created: Long
)

@Serializable
class ChatItems(
    val roomId: String,
    val subject: String,
    val items: List<ChatItem>
) {
    companion object {
        val EMPTY = ChatItems("", "", emptyList())
    }
}

@Serializable
class ChatItem(
    val author: Author,
    val text: String,
    val created: Long,
    val links: List<String>,
    val images: List<String>
)
