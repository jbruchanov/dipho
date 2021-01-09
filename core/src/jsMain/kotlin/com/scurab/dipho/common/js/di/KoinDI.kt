package com.scurab.dipho.common.js.di

import com.scurab.dipho.common.core.ILogger
import com.scurab.dipho.common.core.JsLogger
import com.scurab.dipho.common.nav.Links
import com.scurab.dipho.home.HomeViewModel
import org.koin.dsl.module

object JsModule {
    val koinModule = module {
        single { Links() }
        single<ILogger> { JsLogger() }
    }
}