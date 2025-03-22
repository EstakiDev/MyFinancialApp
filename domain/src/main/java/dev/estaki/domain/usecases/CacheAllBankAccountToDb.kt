package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.repo.reposities.BankAccountRepository
import kotlinx.coroutines.flow.Flow

class CacheAllBankAccountToDb(
    private val bankAccountRepository: BankAccountRepository
) {
    suspend operator fun invoke(bankAccountList: List<BankCardModel>): Flow<List<Long>>{
        return bankAccountRepository.addAll(bankAccountList)
    }
}