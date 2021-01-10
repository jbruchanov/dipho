package com.scurab.dipho.web

import com.scurab.dipho.common.js.nav.Links
import com.scurab.dipho.web.home.HomeComponent
import com.scurab.dipho.web.thread.ThreadComponent
import com.scurab.dipho.web.thread.ThreadProps
import org.koin.core.KoinComponent
import org.koin.core.inject
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.router.dom.hashRouter
import react.router.dom.redirect
import react.router.dom.route
import react.router.dom.switch

class App : RComponent<RProps, RState>(), KoinComponent {

    private val links by inject<Links>()

    override fun RBuilder.render() {
        hashRouter { // or "browserRouter"
            switch {
                route(links.root(), HomeComponent::class, exact = true)
                route<ThreadProps>(links.thread(":threadId")) { params ->
                    child(ThreadComponent::class) {
                        attrs {
                            threadId = params.match.params.threadId
                        }
                    }
                }
                route("/test") {
                    div { +"Test" }
                }
                redirect(from = "*", to = links.root())
            }
        }
    }
}