package com.scurab.dipho.common.di

import com.scurab.dipho.home.HomeViewModel
import org.koin.dsl.module

object CommonModule {
    val koinModule = module {
        factory { HomeViewModel() }
    }
}