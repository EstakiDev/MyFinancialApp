package dev.estaki.data.db.repositoryImpl

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.datasource.SmsDataSource
import dev.estaki.domain.repo.reposities.SmsRepository
import kotlinx.coroutines.flow.Flow

class SmsRepositoryImpl(
    private val localDS: SmsDataSource.Local,
    private val remoteDS:  SmsDataSource.Remote
) : SmsRepository {
    override suspend fun readAll(): Flow<List<SmsModel>> =
        localDS.readAll()

    override suspend fun read(id: Long): Flow<SmsModel> =
        localDS.read(id)

    override suspend fun delete(id: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun add(id: SmsModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<SmsModel>): Flow<List<Long>> =
        localDS.addAll(item)


    override suspend fun getAllCount(): Flow<Long> {
        TODO("Not yet implemented")
    }

}