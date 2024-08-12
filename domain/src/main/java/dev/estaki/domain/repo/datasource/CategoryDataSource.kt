package dev.estaki.domain.repo.datasource

import dev.estaki.domain.base.BaseDataSource
import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel

interface CategoryDataSource {
    interface Local: BaseDataSource<CategoryModel, Int, Long>

    interface Remote:BaseDataSource<CategoryModel,Int,Long>
}