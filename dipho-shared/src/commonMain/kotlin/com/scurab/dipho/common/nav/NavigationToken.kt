package com.scurab.dipho.common.nav

sealed class NavigationToken {
    object Back : NavigationToken()
    object Root : NavigationToken()
    class Thread(val threadId: String) : NavigationToken()
}
