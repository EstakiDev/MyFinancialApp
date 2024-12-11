package dev.estaki.data.db.datasourceImpl

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.datasource.CategoryDataSource
import kotlinx.coroutines.flow.Flow

class CategoryRemoteDataSourceImpl:CategoryDataSource.Remote {
    override suspend fun readAll(): Flow<List<CategoryModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Long): Flow<CategoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(entity: CategoryModel) {
        TODO("Not yet implemented")
    }


    override suspend fun add(id: CategoryModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<CategoryModel>): Flow<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCount(): Flow<Long> {
        TODO("Not yet implemented")
    }

}