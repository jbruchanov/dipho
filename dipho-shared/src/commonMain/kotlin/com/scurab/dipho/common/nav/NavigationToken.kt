package com.scurab.dipho.common.nav

sealed class NavigationToken {
    object Back : NavigationToken()
    object Root : NavigationToken()
    class ChatRoom(val subject: String, val chatRoomId: String) : NavigationToken()
}
