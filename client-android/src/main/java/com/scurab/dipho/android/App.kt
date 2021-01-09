package com.scurab.dipho.android

import android.app.Application
import com.scurab.dipho.common.android.di.AndroidModule
import com.scurab.dipho.common.di.CommonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

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