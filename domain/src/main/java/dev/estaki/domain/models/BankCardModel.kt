package dev.estaki.domain.models

data class BankCardModel(
    val id: Int,
    val bankName: String,
    val bankAccountNumber: String,
    val bankCardBalance: String,
)
