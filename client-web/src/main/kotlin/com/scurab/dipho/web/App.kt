package com.scurab.dipho.web

import com.scurab.dipho.common.nav.Links
import com.scurab.dipho.web.main.MainPage
import org.koin.core.KoinComponent
import org.koin.core.inject
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.router.dom.hashRouter
import react.router.dom.redirect
import react.router.dom.route
import react.router.dom.switch

class App : RComponent<RProps, RState>(), KoinComponent {

    private val links by inject<Links>()

    override fun RBuilder.render() {
        hashRouter { // or "browserRouter"
            switch {
                route(links.root(), MainPage::class, exact = true)
                redirect(from = "*", to = links.root())
            }
        }
    }
}