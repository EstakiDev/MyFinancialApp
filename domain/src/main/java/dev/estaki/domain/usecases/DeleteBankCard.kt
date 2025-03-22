package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.repo.reposities.BankAccountRepository
import kotlinx.coroutines.flow.Flow

class DeleteBankCard(
    private val bankAccountRepository: BankAccountRepository
) {
    suspend operator fun invoke(bankCardModel: BankCardModel): Flow<Int> {
        return bankAccountRepository.delete(bankCardModel)
    }

}