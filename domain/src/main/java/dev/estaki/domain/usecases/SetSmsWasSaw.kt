package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository

class SetSmsWasSaw(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(smsId: Long) {
        smsRepository.setSmsWasSaw(smsId)
    }

}