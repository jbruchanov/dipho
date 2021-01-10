package com.scurab.dipho.common.android.di

import com.scurab.dipho.common.android.core.AndroidLogger
import com.scurab.dipho.common.android.coroutines.AndroidDispatchers
import com.scurab.dipho.common.core.ILogger
import com.scurab.dipho.common.coroutines.IDispatchers
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import org.koin.dsl.module

object AndroidModule {
    val koinModule = module {
        single<ILogger> { AndroidLogger() }
        single<IDispatchers> { AndroidDispatchers() }
        single { HttpClient(OkHttp) { install(JsonFeature) } }
    }
}