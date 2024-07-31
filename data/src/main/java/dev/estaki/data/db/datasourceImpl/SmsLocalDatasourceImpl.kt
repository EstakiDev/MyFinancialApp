package dev.estaki.data.db.datasourceImpl

import dev.estaki.data.db.dao.SmsDao
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.repo.datasource.SmsDataSource

class SmsLocalDatasourceImpl(
    private val smsDao: SmsDao
) : SmsDataSource.Local {
    override fun readAll(): List<SmsModel> {
        TODO("Not yet implemented")
    }

    override fun read(id: Long): SmsModel {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

    override fun update(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun add(id: SmsModel) {
        TODO("Not yet implemented")
    }

    override fun addAll(item: List<SmsModel>) {
        TODO("Not yet implemented")
    }
}