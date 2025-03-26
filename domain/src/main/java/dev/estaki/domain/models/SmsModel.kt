package dev.estaki.domain.models

data class SmsModel(
    val id:Long?,
    val bankName:String,
    val bankAccountNumber:String,
    var transactionType: TransactionType,
    val transactionAmount:String,
    val transactionDate:String,
    val transactionDateTime: Long,
    val transactionTime:String,
    val bankCardBalance:String,
    var categoryIds:List<Long>,
    val description:String?,
    val smsSender:String,
    val smsBody:String,
    val isModified: Boolean = false,
    val isSeen: Boolean = false,
    )
