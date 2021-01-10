package com.scurab.dipho.common.nav

import com.scurab.dipho.nav.INavigationToken

sealed class NavigationToken : INavigationToken {
    object Back : NavigationToken()
    object Root : NavigationToken()
    class Thread(val threadId: String) : NavigationToken()
}
