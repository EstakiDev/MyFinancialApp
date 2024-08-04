package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetAllSms(
    private val smsRepository: SmsRepository
) {

    suspend operator fun invoke(): Flow<List<SmsModel>> {
        return smsRepository.readAll()
    }

}