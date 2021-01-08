package com.scurab.dipho.common.di

import com.scurab.dipho.common.nav.Links
import com.scurab.dipho.home.HomeViewModel
import org.koin.dsl.module

object JsModule {
    val koinModule = module {
        single { Links() }
        factory { HomeViewModel() }
    }
}