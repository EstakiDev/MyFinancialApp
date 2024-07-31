package dev.estaki.domain.repo.reposities

import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.SmsModel

interface SmsRepository:BaseRepository<SmsModel,Int,Long> {
}