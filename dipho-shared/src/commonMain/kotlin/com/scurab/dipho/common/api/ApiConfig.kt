package com.scurab.dipho.common.api

class ApiConfig(val url: String = "http://127.0.0.1:8080/api") {
    fun chatRooms(nextPageId: String? = null) = "$url/chatrooms" + (nextPageId?.let { "/$it" } ?: "")
    fun chatRoom(chatRoomId: String) = "$url/chatroom/$chatRoomId"
}