package dev.estaki.myFinancialApp.presentation.actions

sealed interface MainScreenActions {
    data class LoadSms(val bankAccountNumber: String= ""): MainScreenActions
    data class ReloadSmsByScrollCards(val bankAccountNumber: String= ""): MainScreenActions
    object OpenSms: MainScreenActions
}