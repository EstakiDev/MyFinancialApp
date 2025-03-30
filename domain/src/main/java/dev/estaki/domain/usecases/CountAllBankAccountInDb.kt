package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.repo.reposities.BankAccountRepository
import kotlinx.coroutines.flow.Flow

class CountAllBankAccountInDb(
    private val bankAccountRepository: BankAccountRepository
) {
    suspend operator fun invoke(): Flow<List<BankCardModel>>{
        return bankAccountRepository.readAll()
    }
}