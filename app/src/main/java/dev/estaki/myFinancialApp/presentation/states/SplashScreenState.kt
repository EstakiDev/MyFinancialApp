package dev.estaki.myFinancialApp.presentation.states

data class SplashScreenState(
    val isLoading: Boolean = true,
    val isFinishedAndGoToMainScreen: Boolean = false,
    val mustGetPermissions: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
