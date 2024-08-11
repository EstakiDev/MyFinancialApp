package dev.estaki.data.db.repositoryImpl

import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.datasource.CategoryDataSource
import dev.estaki.domain.repo.datasource.SmsDataSource
import dev.estaki.domain.repo.reposities.CategoryRepository
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override suspend fun addAll(item: List<CategoryModel>) {
        localDS.addAll(item)
    }

}