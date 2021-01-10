package com.scurab.dipho.common.di

import com.scurab.dipho.common.api.ApiConfig
import com.scurab.dipho.common.api.IServerApi
import com.scurab.dipho.common.api.ServerApi
import com.scurab.dipho.home.HomeViewModel
import com.scurab.dipho.home.ThreadViewModel
import org.koin.dsl.module

object CommonModule {
    val koinModule = module {
        factory { HomeViewModel() }
        factory { ThreadViewModel() }
        single { ApiConfig() }
        single<IServerApi> { ServerApi(get(), get()) }
    }
}