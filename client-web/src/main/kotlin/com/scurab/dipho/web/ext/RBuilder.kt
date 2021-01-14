package com.scurab.dipho.web.ext

import kotlinx.html.A
import react.RBuilder
import react.dom.RDOMBuilder
import react.dom.a

fun RBuilder.aTab(
    href: String? = null,
    classes: String? = null,
    block: RDOMBuilder<A>.() -> Unit
) = a(href, target = "_blank", classes) {
    block(this)
}