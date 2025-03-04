package dev.estaki.domain.repo.reposities

import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import kotlinx.coroutines.flow.Flow

interface SmsRepository:BaseRepository<SmsModel,Int,Long> {
    suspend fun getSmsByBankAccountNumber(accountNumber:String):Flow<List<SmsModel>>
    suspend fun getAllBankAccountNumber():Flow<List<BankCardModel>>
}