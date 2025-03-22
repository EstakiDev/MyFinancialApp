package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.BankAccountRepository
import dev.estaki.domain.repo.reposities.SmsRepository

class UpsertBankCard(
    private val bankAccountRepository: BankAccountRepository
) {
    suspend operator fun invoke(bankCardModel: BankCardModel) {
        bankAccountRepository.upsert(bankCardModel)
    }

}