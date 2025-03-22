package dev.estaki.domain.repo.reposities

import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import kotlinx.coroutines.flow.Flow

interface BankAccountRepository:BaseRepository<BankCardModel,Int,Long> {
    suspend fun getByBankAccountNumber(accountNumber:String):Flow<BankCardModel>
    suspend fun getAllBankAccountNumber():Flow<List<BankCardModel>>
}