package com.scurab.dipho.web.main

import org.koin.core.KoinComponent
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class MainPage(props: RProps) : RComponent<RProps, RState>(props), KoinComponent {

    override fun RBuilder.render() {
        div {
            +"MainPage"
        }
    }
}