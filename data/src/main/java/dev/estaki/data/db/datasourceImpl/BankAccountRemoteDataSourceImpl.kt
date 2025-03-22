package dev.estaki.data.db.datasourceImpl

import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.CategoryModel
import dev.estaki.domain.repo.datasource.BankAccountDataSource
import dev.estaki.domain.repo.datasource.CategoryDataSource
import kotlinx.coroutines.flow.Flow

class BankAccountRemoteDataSourceImpl: BankAccountDataSource.Remote {
    override suspend fun readAll(): Flow<List<BankCardModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Long): Flow<BankCardModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: BankCardModel): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(entity: BankCardModel) {
        TODO("Not yet implemented")
    }

    override suspend fun add(entity: BankCardModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<BankCardModel>): Flow<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCount(): Flow<Long> {
        TODO("Not yet implemented")
    }

}