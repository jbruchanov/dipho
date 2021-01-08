package com.scurab.dipho.android

import android.app.Application
import com.scurab.dipho.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            loadKoinModules(module {
                viewModel { HomeViewModel() }
            })
        }
    }
}