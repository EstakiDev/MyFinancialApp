package dev.estaki.myFinancialApp.presentation.actions


sealed interface MainScreenActions {
    data class LoadSmsFromDb(val bankAccountNumber: String= ""): MainScreenActions
    data class ReloadSmsByScrollCards(val bankAccountNumber: String= ""): MainScreenActions
    object OpenSms: MainScreenActions
}