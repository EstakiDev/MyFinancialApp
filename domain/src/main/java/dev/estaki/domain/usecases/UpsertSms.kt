package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository

class UpsertSms(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(smsModel: SmsModel) {
        smsRepository.upsert(smsModel)
    }

}