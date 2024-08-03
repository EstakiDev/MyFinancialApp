package dev.estaki.domain.models

data class SmsModel(
    val id:Long,
    val bankName:String,
    val bankAccountNumber:String,
    val transactionType: TransactionType,
    val transactionAmount:String,
    val transactionDate:String,
    val transactionTime:String,
    val bankCardBalance:String,
    val categoryId:Long,
    )
