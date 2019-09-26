package com.cascer.favoritemovieapp

import android.app.Application
import com.cascer.favoritemovieapp.di.appModule
import com.cascer.favoritemovieapp.di.networkModule
import com.cascer.favoritemovieapp.di.viewModelModule
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(appModule, networkModule, viewModelModule),
            logger = AndroidLogger(showDebug = true)
        )
    }
}