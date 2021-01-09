package com.scurab.dipho.android

import android.app.Application
import com.scurab.dipho.common.android.di.AndroidModule
import com.scurab.dipho.common.di.CommonModule
import com.scurab.dipho.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            loadKoinModules(CommonModule.koinModule)
            loadKoinModules(AndroidModule.koinModule)
        }
    }
}