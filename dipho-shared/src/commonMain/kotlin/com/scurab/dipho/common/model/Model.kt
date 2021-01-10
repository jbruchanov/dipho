package com.scurab.dipho.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val id: String,
    val name: String
)

@Serializable
data class ChatRooms(
    val nextPageId: String,
    val items: List<ChatRoom>
)

@Serializable
data class ChatRoom(
    val id: String,
    val subject: String,
    val author: Author,
    val messages: Int
)

@Serializable
data class ChatItems(
    val subject: String,
    val items: List<ChatItem>
)

@Serializable
data class ChatItem(
    val author: Author,
    val text: String,
    val created: Long,
    val links: List<String>,
    val images: List<String>
)
