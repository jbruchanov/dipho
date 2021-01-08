package com.scurab.dipho.common.di

import com.scurab.dipho.common.nav.Links
import org.koin.dsl.module

object JsModule {
    val koinModule = module {
        single { Links() }
    }
}