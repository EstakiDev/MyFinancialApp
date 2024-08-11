package dev.estaki.domain.usecases

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.CategoryRepository
import dev.estaki.domain.repo.reposities.SmsRepository

class CacheCategoryToDb(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(listModel:List<CategoryModel>){
        categoryRepository.addAll(listModel)
    }

}