package dev.estaki.myFinancialApp.presentation.actions

import android.content.ContentResolver

sealed interface SplashScreenActions {
    data class ExtractSmsFromContentResolver(val contentResolver: ContentResolver): SplashScreenActions
    object MustGoToMainScreen: SplashScreenActions
}