package dev.estaki.myFinancialApp.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.estaki.data.db.dao.CategoryDao
import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.db.datasourceImpl.CategoryLocalDataSourceImpl
import dev.estaki.data.db.datasourceImpl.CategoryRemoteDataSourceImpl
import dev.estaki.data.db.datasourceImpl.SmsLocalDatasourceImpl
import dev.estaki.data.db.datasourceImpl.SmsRemoteDatasourceImpl
import dev.estaki.data.db.repositoryImpl.CategoryRepositoryImpl
import dev.estaki.data.db.repositoryImpl.SmsRepositoryImpl
import dev.estaki.domain.repo.datasource.CategoryDataSource
import dev.estaki.domain.repo.datasource.SmsDataSource
import dev.estaki.domain.repo.reposities.CategoryRepository
import dev.estaki.domain.repo.reposities.SmsRepository
import dev.estaki.domain.usecases.CacheCategoryToDb
import dev.estaki.domain.usecases.CacheSmsToDb
import dev.estaki.domain.usecases.GetAllBankAccountNumber
import dev.estaki.domain.usecases.GetAllCategoryCount
import dev.estaki.domain.usecases.GetAllCategoryList
import dev.estaki.domain.usecases.GetAllSms
import dev.estaki.domain.usecases.GetAllSmsByBankAccountNumber
import dev.estaki.domain.usecases.GetSingleSms
import dev.estaki.domain.usecases.UpsertSms
import dev.estaki.myFinancialApp.SmsReceiver
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
    @Singleton
    fun provideSmsRemoveDataSource(): SmsDataSource.Remote {
        return SmsRemoteDatasourceImpl()
    }

    @Provides
    @Singleton
    fun provideSmsLocalDataSource(smsDao: SmsDao): SmsDataSource.Local {
        return SmsLocalDatasourceImpl(smsDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryLocalDs: CategoryDataSource.Local,
        categoryRemoteDs: CategoryDataSource.Remote
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryLocalDs, categoryRemoteDs)
    }

    @Provides
    @Singleton
    fun provideCategoryRemoveDataSource(): CategoryDataSource.Remote {
        return CategoryRemoteDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(categoryDao: CategoryDao): CategoryDataSource.Local {
        return CategoryLocalDataSourceImpl(categoryDao)
    }

}