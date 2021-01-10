package com.scurab.dipho.common.js.di

import com.scurab.dipho.common.core.ILogger
import com.scurab.dipho.common.coroutines.IDispatchers
import com.scurab.dipho.common.js.core.JsLogger
import com.scurab.dipho.common.js.coroutines.JsDispatchers
import com.scurab.dipho.common.js.nav.JsNavigator
import com.scurab.dipho.common.js.nav.Links
import com.scurab.dipho.nav.INavigator
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import org.koin.dsl.module

object JsModule {
    val koinModule = module {
        single { Links() }
        single<ILogger> { JsLogger() }
        single<IDispatchers> { JsDispatchers() }
        single<INavigator> { JsNavigator(get()) }
        single {
            HttpClient(Js) {
                //FIXME:failing
                /*install(JsonFeature)*/
            }
        }
    }
}