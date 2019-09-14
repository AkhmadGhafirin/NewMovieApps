package com.cascer.madesubmission2

import android.app.Application
import com.cascer.madesubmission2.di.appModule
import com.cascer.madesubmission2.di.networkModule
import com.cascer.madesubmission2.di.viewModelModule
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