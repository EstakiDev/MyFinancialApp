package dev.estaki.data.db.repositoryImpl

import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.datasource.SmsDataSource
import dev.estaki.domain.repo.reposities.SmsRepository

class SmsRepositoryImpl(
    private val localDS: SmsDataSource.Local,
    private val remoteDS:  SmsDataSource.Remote
) : SmsRepository {
    override suspend fun readAll(): List<SmsModel> {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Long): SmsModel {
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

    override suspend fun add(id: SmsModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(item: List<SmsModel>) {
        localDS.addAll(item)
    }

}