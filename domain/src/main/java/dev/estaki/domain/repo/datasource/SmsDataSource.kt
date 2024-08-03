package dev.estaki.domain.repo.datasource

import dev.estaki.domain.base.BaseDataSource
import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.SmsModel

interface SmsDataSource {
    interface Local: BaseDataSource<SmsModel, Int, Long> {
    }

    interface Remote:BaseDataSource<SmsModel,Int,Long> {
    }
}