package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.BankAccountRepository
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetSingleBankAccount(
    private val bankAccountRepository: BankAccountRepository
) {

    suspend operator fun invoke(accountNumber: String): Flow<BankCardModel> {
        return bankAccountRepository.getByBankAccountNumber(accountNumber)
    }

}