package dev.estaki.myFinancialApp.presentation.actions

import dev.estaki.domain.models.SmsModel

sealed interface TransactionDetailScreenActions {
    data class LoadTransaction(val smsId: Long): TransactionDetailScreenActions
    data class SaveTransaction(val smsModel: SmsModel): TransactionDetailScreenActions
    data class DeleteTransaction(val smsModel: SmsModel): TransactionDetailScreenActions
}