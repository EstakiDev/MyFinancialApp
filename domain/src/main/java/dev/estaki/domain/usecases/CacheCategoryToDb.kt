package dev.estaki.domain.usecases

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.reposities.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CacheCategoryToDb(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(listModel:List<CategoryModel>): Flow<List<Long>> =
        categoryRepository.addAll(listModel)

}