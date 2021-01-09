package com.scurab.dipho.common.js.di

import com.scurab.dipho.common.core.ILogger
import com.scurab.dipho.common.js.core.JsLogger
import com.scurab.dipho.common.js.nav.Links
import org.koin.dsl.module

object JsModule {
    val koinModule = module {
        single { Links() }
        single<ILogger> { JsLogger() }
    }
}