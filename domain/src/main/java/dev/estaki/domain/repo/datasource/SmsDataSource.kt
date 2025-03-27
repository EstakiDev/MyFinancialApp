package dev.estaki.domain.repo.datasource

import dev.estaki.domain.base.BaseDataSource
import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import kotlinx.coroutines.flow.Flow

interface SmsDataSource {
    interface Local: BaseDataSource<SmsModel, Int, Long> {
        suspend fun getSmsByBankAccountNumber(accountNumber:String):Flow<List<SmsModel>>
        suspend fun getAllBankAccountNumber():Flow<List<BankCardModel>>
        suspend fun setSmsWasSaw(smsId: Long)

    }

    interface Remote:BaseDataSource<SmsModel,Int,Long> {
    }
}