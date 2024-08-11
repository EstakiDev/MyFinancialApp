package dev.estaki.data.db.datasourceImpl

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.datasource.CategoryDataSource
import kotlinx.coroutines.flow.Flow

class CategoryLocalDataSourceImpl:CategoryDataSource.Local {
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

    override suspend fun update(id: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun add(id: CategoryModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<CategoryModel>) {
        TODO("Not yet implemented")
    }

}