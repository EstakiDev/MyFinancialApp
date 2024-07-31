package dev.estaki.domain.repo.datasource

import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.SmsModel

interface SmsDataSource {
    interface Local: BaseRepository<SmsModel, Int, Long> {
    }

    interface Remote:BaseRepository<SmsModel,Int,Long> {
    }
}