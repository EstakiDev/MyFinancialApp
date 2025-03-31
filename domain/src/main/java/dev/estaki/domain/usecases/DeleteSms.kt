package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class DeleteSms(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(smsModel: SmsModel): Flow<Int> =
        smsRepository.delete(smsModel)


}