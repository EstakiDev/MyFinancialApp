package dev.estaki.domain.repo.datasource

import dev.estaki.domain.base.BaseDataSource
import dev.estaki.domain.models.BankCardModel
import kotlinx.coroutines.flow.Flow

interface BankAccountDataSource {
    interface Local: BaseDataSource<BankCardModel, Int, Long> {
        suspend fun getByBankAccountNumber(accountNumber:String):Flow<BankCardModel>
        suspend fun getAllBankAccountNumber():Flow<List<BankCardModel>>
    }

    interface Remote:BaseDataSource<BankCardModel,Int,Long> {
    }
}