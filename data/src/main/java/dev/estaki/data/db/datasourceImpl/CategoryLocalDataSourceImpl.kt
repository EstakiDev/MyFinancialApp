package dev.estaki.data.db.datasourceImpl

import dev.estaki.data.db.dao.CategoryDao
import dev.estaki.data.mapper.toDbEntity
import dev.estaki.data.mapper.toDomainModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.datasource.CategoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryLocalDataSourceImpl(private val categoryDao: CategoryDao):CategoryDataSource.Local {
    override suspend fun readAll(): Flow<List<CategoryModel>> = flow { emit(categoryDao.selectAll().map { it.toDomainModel() }) }.flowOn(Dispatchers.IO)

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

    override suspend fun addAll(item: List<CategoryModel>) =
        flow { emit(categoryDao.insertCategory(item.map { it.toDbEntity() })) }.flowOn(Dispatchers.IO)

    override suspend fun getAllCount(): Flow<Long> =
        flow { emit(categoryDao.getCategoryCount()) }.flowOn(Dispatchers.IO).flowOn(Dispatchers.IO)


}