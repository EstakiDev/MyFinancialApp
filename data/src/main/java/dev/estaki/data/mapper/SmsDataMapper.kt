package dev.estaki.data.mapper

import dev.estaki.data.entities.CategoryEntity
import dev.estaki.data.entities.SmsEntity
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel

fun SmsModel.toDbEntity() = SmsEntity(
    id = 0L,
    bankName = bankName,
    bankAccountNumber = bankAccountNumber,
    transactionType = transactionType,
    transactionAmount = transactionAmount,
    transactionDate = transactionDate,
    transactionTime = transactionTime,
    bankCardBalance = bankCardBalance,
    categoryId = categoryId,
    description = description

    )

fun SmsEntity.toDomainModel() = SmsModel(
    id = id,
    bankName = bankName,
    bankAccountNumber = bankAccountNumber,
    transactionType = transactionType,
    transactionAmount = transactionAmount,
    transactionDate = transactionDate,
    transactionTime = transactionTime,
    bankCardBalance = bankCardBalance,
    categoryId = categoryId,
    description = description

)

fun CategoryModel.toDbEntity() = CategoryEntity(
    id = 0L,
    title = title
)

fun CategoryEntity.toDomainModel() = CategoryModel(
    id = id,
    title = title
)