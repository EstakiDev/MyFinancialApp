package dev.estaki.data.db.repositoryImpl

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.datasource.CategoryDataSource
import dev.estaki.domain.repo.reposities.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val localDS: CategoryDataSource.Local,
    private val remoteDS:  CategoryDataSource.Remote
) : CategoryRepository {
    override suspend fun readAll(): Flow<List<CategoryModel>> =
        localDS.readAll()

    override suspend fun read(id: Long): CategoryModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun add(id: CategoryModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<CategoryModel>): Flow<List<Long>> =
        localDS.addAll(item)


    override suspend fun getAllCount(): Flow<Long> =
        localDS.getAllCount()

}