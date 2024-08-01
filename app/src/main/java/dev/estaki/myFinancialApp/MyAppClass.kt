package dev.estaki.myFinancialApp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyAppClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: MY_APP_CLASS")
    }
}