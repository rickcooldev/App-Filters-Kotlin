package com.example.appfilterskotlin.utilities

import android.app.Application
import com.example.appfilterskotlin.dependencyinjection.repositoryModule
import com.example.appfilterskotlin.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}