package dev.estaki.data.db.datasourceImpl

import dev.estaki.data.db.dao.BankAccountDao
import dev.estaki.data.mapper.toDbEntity
import dev.estaki.data.mapper.toDomainModel
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.repo.datasource.BankAccountDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BankAccountLocalDataSourceImpl(private val bankAccountDao: BankAccountDao) :
    BankAccountDataSource.Local {
    override suspend fun readAll(): Flow<List<BankCardModel>> =
        flow { emit(
            bankAccountDao.readAll().map { it.toDomainModel() }
        ) }.flowOn(Dispatchers.IO)

    override suspend fun read(id: Long): Flow<BankCardModel> =
        flow { emit(bankAccountDao.read(id).toDomainModel()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteById(id: Long): Flow<Int> =
    flow { emit(bankAccountDao.deleteById(id)) }.flowOn(Dispatchers.IO)

    override suspend fun delete(entity: BankCardModel): Flow<Int> =
        flow { emit(bankAccountDao.delete(entity.toDbEntity())) }.flowOn(Dispatchers.IO)

    override suspend fun deleteAll(): Flow<Int> =
        flow { emit(bankAccountDao.deleteAll()) }.flowOn(Dispatchers.IO)


    override suspend fun upsert(entity: BankCardModel) =
        withContext(Dispatchers.IO){
            bankAccountDao.upsert(entity.toDbEntity())
        }

    override suspend fun add(entity: BankCardModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<BankCardModel>): Flow<List<Long>> =
        flow {
            emit(
                bankAccountDao.insertAll(item.map { it.toDbEntity() }
                ))
        }.flowOn(Dispatchers.IO)

    override suspend fun getAllCount(): Flow<Long> =
        flow {
            emit(
                bankAccountDao.getAllCount()
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun getByBankAccountNumber(accountNumber: String): Flow<BankCardModel> =
        flow {
            emit(
                bankAccountDao.readByBankAccountNumber(accountNumber).toDomainModel()
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun getAllBankAccountNumber(): Flow<List<BankCardModel>> {
        TODO("Not yet implemented")
    }


}