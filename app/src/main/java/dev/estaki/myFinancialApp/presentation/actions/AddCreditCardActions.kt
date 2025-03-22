package dev.estaki.myFinancialApp.presentation.actions

import dev.estaki.domain.models.BankCardModel

sealed interface AddCreditCardActions {
    data class LoadCard(val bankAccountNumber: String= ""): AddCreditCardActions
    data class SaveCard(val bankCardModel: BankCardModel): AddCreditCardActions
    data class DeleteCard(val bankCardModel: BankCardModel): AddCreditCardActions
}