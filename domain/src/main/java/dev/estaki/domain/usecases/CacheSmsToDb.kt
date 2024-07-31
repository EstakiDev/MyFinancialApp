package dev.estaki.domain.usecases

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.SmsRepository

class CacheSmsToDb constructor(
    private val smsRepository: SmsRepository
) {

    suspend operator fun invoke(listSmsModel:List<SmsModel>){

    }

}