package dev.estaki.myFinancialApp.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.db.datasourceImpl.SmsLocalDatasourceImpl
import dev.estaki.data.db.datasourceImpl.SmsRemoteDatasourceImpl
import dev.estaki.data.db.repositoryImpl.SmsRepositoryImpl
import dev.estaki.domain.repo.datasource.SmsDataSource
import dev.estaki.domain.repo.reposities.SmsRepository
import dev.estaki.domain.usecases.CacheSmsToDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSmsRepository(
        smsLocalDs: SmsDataSource.Local,
        smsRemoteDs: SmsDataSource.Remote
    ): SmsRepository {
        return SmsRepositoryImpl(smsLocalDs, smsRemoteDs)
    }

    @Provides
    fun provideCacheSmsUseCase(smsRepository:SmsRepository): CacheSmsToDb {
        return CacheSmsToDb(smsRepository)
    }

    @Provides
    @Singleton
    fun provideSmsRemoveDataSource(): SmsDataSource.Remote {
        return SmsRemoteDatasourceImpl()
    }

    @Provides
    @Singleton
    fun provideSmsLocalDataSource(smsDao: SmsDao): SmsDataSource.Local {
        return SmsLocalDatasourceImpl(smsDao)
    }
}