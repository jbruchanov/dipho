package com.scurab.dipho.common.android.di

import com.scurab.dipho.common.android.core.AndroidLogger
import com.scurab.dipho.common.core.ILogger
import org.koin.dsl.module

object AndroidModule {
    val koinModule = module {
        single<ILogger> { AndroidLogger() }
    }
}