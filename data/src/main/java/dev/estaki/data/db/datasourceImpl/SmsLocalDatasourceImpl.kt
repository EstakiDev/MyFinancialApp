package dev.estaki.data.db.datasourceImpl

import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.entities.SmsEntity
import dev.estaki.data.mapper.toDbEntity
import dev.estaki.data.mapper.toDomainModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.datasource.SmsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SmsLocalDatasourceImpl(
    private val smsDao: SmsDao
) : SmsDataSource.Local {
    override suspend fun readAll(): Flow<List<SmsModel>> =
        flow {emit(smsDao.readAll().map { it.toDomainModel() })}.flowOn(Dispatchers.IO)

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

    override suspend fun addAll(item: List<SmsModel>) {
        withContext(Dispatchers.IO) {
            smsDao.insertAll(item.map {
                it.toDbEntity()
            })
        }
    }
}