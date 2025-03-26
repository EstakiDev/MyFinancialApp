package dev.estaki.myFinancialApp.presentation.actions


sealed interface MainScreenActions {
    object LoadCardsFromDb: MainScreenActions
    data class ReloadSmsByScrollCards(val bankAccountNumber: String= ""): MainScreenActions
    object OpenSms: MainScreenActions
}