package dev.estaki.myFinancialApp.presentation.states

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel

data class MainScreenState(
    val smsList:List<SmsModel> = emptyList(),
    val listBankAccountNumber:List<BankCardModel> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
