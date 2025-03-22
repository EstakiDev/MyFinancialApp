package dev.estaki.data.mapper

import dev.estaki.data.entities.BankAccountEntity
import dev.estaki.domain.models.BankCardModel

fun BankCardModel.toDbEntity() = BankAccountEntity(
    id = id,
    bankName = bankName,
    bankAccountNumber = bankAccountNumber,
    bankCardBalance = bankCardBalance,
    bankCardNumber = bankCardNumber
)

fun BankAccountEntity.toDomainModel() = BankCardModel(
    id = id,
    bankName = bankName,
    bankAccountNumber = bankAccountNumber,
    bankCardBalance = bankCardBalance,
    bankCardNumber = bankCardNumber

)
