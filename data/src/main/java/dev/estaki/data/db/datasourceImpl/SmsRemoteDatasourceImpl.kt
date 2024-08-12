package dev.estaki.data.db.datasourceImpl

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.datasource.SmsDataSource
import kotlinx.coroutines.flow.Flow

class SmsRemoteDatasourceImpl: SmsDataSource.Remote {
    override suspend fun readAll(): Flow<List<SmsModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Long): Flow<SmsModel> {
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

    override suspend fun add(id: SmsModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<SmsModel>): Flow<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCount(): Flow<Long> {
        TODO("Not yet implemented")
    }

}