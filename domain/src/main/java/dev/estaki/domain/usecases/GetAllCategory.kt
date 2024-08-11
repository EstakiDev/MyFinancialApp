package dev.estaki.domain.usecases

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.reposities.CategoryRepository
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategory(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): Flow<List<CategoryModel>> =
        categoryRepository.readAll()


}