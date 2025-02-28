package dev.estaki.myFinancialApp.presentation.intent

import dev.estaki.myFinancialApp.presentation.states.MainScreenState

sealed interface MainScreenActions {
    data class LoadSms(val bankAccountNumber: String= ""): MainScreenActions
    object OpenSms: MainScreenActions
}