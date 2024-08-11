package dev.estaki.domain.repo.reposities

import dev.estaki.domain.base.BaseRepository
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel

interface CategoryRepository:BaseRepository<CategoryModel,Int,Long> {
}