package dev.estaki.myFinancialApp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyAppClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree());
        Timber.tag("TAG").d("onCreate: MY_APP_CLASS")
    }
}