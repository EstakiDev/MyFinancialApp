package dev.estaki.domain.usecases

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class GetAllBankAccountNumberFromTbSms(
    private val smsRepository: SmsRepository
) {

    suspend operator fun invoke(): Flow<List<BankCardModel>> {
        return smsRepository.getAllBankAccountNumber()
    }

}