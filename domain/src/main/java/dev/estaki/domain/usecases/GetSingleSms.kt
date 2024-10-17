package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetSingleSms(
    private val smsRepository: SmsRepository
) {

    suspend operator fun invoke(id:Long): Flow<SmsModel> {
        return smsRepository.read(id)
    }

}