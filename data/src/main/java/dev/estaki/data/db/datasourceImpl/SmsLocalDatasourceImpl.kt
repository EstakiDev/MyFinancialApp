package dev.estaki.data.db.datasourceImpl

import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.mapper.toDbEntity
import dev.estaki.data.mapper.toDomainModel
import dev.estaki.domain.models.BankCardModel
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
        flow { emit(smsDao.readAll().map { it.toDomainModel() }) }.flowOn(Dispatchers.IO)

    override suspend fun read(id: Long): Flow<SmsModel> =
        flow { emit(
            smsDao.read(id).toDomainModel()
        ) }.flowOn(Dispatchers.IO)

    override suspend fun deleteById(id: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: SmsModel): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(entity: SmsModel) {
        withContext(Dispatchers.IO) {
            val en = entity.toDbEntity()
            smsDao.upsert(en)
        }
    }

    override suspend fun add(entity: SmsModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<SmsModel>): Flow<List<Long>> =
        flow { emit(smsDao.insertAll(item.map { it.toDbEntity() })) }.flowOn(Dispatchers.IO)


    override suspend fun getAllCount(): Flow<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getSmsByBankAccountNumber(accountNumber: String): Flow<List<SmsModel>> =
        flow { emit(smsDao.readByBankAccountNumber(accountNumber).map { it.toDomainModel() }) }.flowOn(Dispatchers.IO)

    override suspend fun getAllBankAccountNumber(): Flow<List<BankCardModel>> =
        flow { emit(smsDao.readAllBankAccountNumber().map { it.toDomainModel() }) }.flowOn(Dispatchers.IO)

    override suspend fun setSmsWasSaw(smsId: Long) {
        withContext(Dispatchers.IO) {
            smsDao.setSmsWasSaw(smsId)
        }
    }
}