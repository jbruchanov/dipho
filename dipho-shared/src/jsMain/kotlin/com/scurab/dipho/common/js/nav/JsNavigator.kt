package com.scurab.dipho.common.js.nav

import com.scurab.dipho.common.lifecycle.ILifecycleOwner
import com.scurab.dipho.common.lifecycle.LifecycleObservable
import com.scurab.dipho.common.nav.NavigationToken
import com.scurab.dipho.nav.INavigator
import kotlinx.browser.window
import org.koin.core.context.KoinContextHandler

class JsNavigator(private val links: Links) : INavigator {
    override fun back() = window.history.back()
    override fun home() = openUrl(links.root())
    override fun thread(threadId: String) = openUrl(links.thread(threadId))

    private fun openUrl(url: String) {
        window.open("/#$url", target = "_self")
    }
}


fun LifecycleObservable<NavigationToken>.bind(lifecycleOwner: ILifecycleOwner, navigator: INavigator = KoinContextHandler.get().get()) {
    observe(lifecycleOwner) {
        when (it) {
            NavigationToken.Back -> navigator.back()
            NavigationToken.Root -> navigator.home()
            is NavigationToken.ChatRoom -> navigator.thread(it.chatRoomId)
        }
    }
}