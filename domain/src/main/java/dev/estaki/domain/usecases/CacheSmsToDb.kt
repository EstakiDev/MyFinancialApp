package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class CacheSmsToDb(
    private val smsRepository: SmsRepository
) {

    suspend operator fun invoke(listSmsModel:List<SmsModel>): Flow<List<Long>> =
        smsRepository.addAll(listSmsModel)


}