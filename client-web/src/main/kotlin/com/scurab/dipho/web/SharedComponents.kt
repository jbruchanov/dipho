package com.scurab.dipho.web

import kotlinx.html.classes
import react.RProps
import react.dom.div
import react.functionalComponent

object SharedComponents {

    private object Css {
        val slider = setOf("slider")
        val line = setOf("slider-line")
        val sublineInc = setOf("slider-subline", "slider-inc")
        val sublineDec = setOf("slider-subline", "slider-dec")
    }

    fun progressBar(isVisible: Boolean) = functionalComponent<RProps>("ProgressBar") {
        div {
            attrs.classes = Css.slider
            if (isVisible) {
                div { attrs.classes = Css.line }
                div { attrs.classes = Css.sublineInc }
                div { attrs.classes = Css.sublineDec }
            }
        }
    }
}
