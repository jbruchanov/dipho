package com.scurab.dipho.common.api

class ApiConfig {
    //FIXME: dynamic based on weburl
    val url = "http://127.0.0.1:8080/api"
    fun chatRooms(nextPageId: String? = null) = "$url/chatrooms" + (nextPageId?.let { "/$it" } ?: "")
    fun chatRoom(chatRoomId: String) = "$url/chatroom/$chatRoomId"
}