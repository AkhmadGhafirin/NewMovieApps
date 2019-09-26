package com.cascer.madesubmission2

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.cascer.madesubmission2.di.appModule
import com.cascer.madesubmission2.di.networkModule
import com.cascer.madesubmission2.di.viewModelModule
import com.cascer.madesubmission2.utils.NotificationHelper
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
        NotificationHelper().createNotificationChannel(
            this, NotificationManagerCompat.IMPORTANCE_HIGH,
            false, getString(R.string.app_name), "Notification Channel ID"
        )
    }
}