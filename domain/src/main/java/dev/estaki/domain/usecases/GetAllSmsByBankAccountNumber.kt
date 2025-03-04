package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class GetAllSmsByBankAccountNumber(
    private val smsRepository: SmsRepository
) {

    suspend operator fun invoke(bankAccountNumber: String): Flow<List<SmsModel>> {
        return smsRepository.getSmsByBankAccountNumber(bankAccountNumber)
    }

}