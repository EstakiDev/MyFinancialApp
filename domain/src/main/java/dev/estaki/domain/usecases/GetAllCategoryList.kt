package dev.estaki.domain.usecases

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.reposities.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoryList(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Flow<List<CategoryModel>> {
       return categoryRepository.readAll()
    }
}