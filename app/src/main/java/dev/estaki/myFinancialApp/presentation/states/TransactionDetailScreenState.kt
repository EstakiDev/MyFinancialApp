package dev.estaki.myFinancialApp.presentation.states

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel

data class TransactionDetailScreenState(
    val smsModel:SmsModel? = null,
    var categoryList:List<CategoryModel> = emptyList(),
    var bankCardList:List<BankCardModel> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
