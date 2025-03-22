package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.repo.reposities.BankAccountRepository
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class GetAllBankCardFromTbBankCard(
    private val bankAccountRepository: BankAccountRepository
) {

    suspend operator fun invoke(): Flow<List<BankCardModel>> {
        return bankAccountRepository.readAll()
    }

}