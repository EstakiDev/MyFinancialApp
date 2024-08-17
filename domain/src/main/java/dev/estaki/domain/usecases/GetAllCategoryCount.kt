package dev.estaki.domain.usecases

import dev.estaki.domain.repo.reposities.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoryCount(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): Flow<Long> =
        categoryRepository.getAllCount()


}