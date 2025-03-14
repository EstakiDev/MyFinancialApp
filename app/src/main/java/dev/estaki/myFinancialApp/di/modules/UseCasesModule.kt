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
import dev.estaki.domain.sharedPrefrence.PreferenceHelper
import dev.estaki.domain.usecases.CacheCategoryToDb
import dev.estaki.domain.usecases.CacheSmsToDb
import dev.estaki.domain.usecases.GetAllBankAccountNumber
import dev.estaki.domain.usecases.GetAllCategoryCount
import dev.estaki.domain.usecases.GetAllCategoryList
import dev.estaki.domain.usecases.GetAllSms
import dev.estaki.domain.usecases.GetAllSmsByBankAccountNumber
import dev.estaki.domain.usecases.GetFirstOpenApp
import dev.estaki.domain.usecases.GetSingleSms
import dev.estaki.domain.usecases.SaveFirstAppOpen
import dev.estaki.domain.usecases.UpsertSms
import dev.estaki.myFinancialApp.SmsReceiver
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun provideCacheSmsUseCase(smsRepository: SmsRepository): CacheSmsToDb {
        return CacheSmsToDb(smsRepository)
    }

    @Provides
    fun provideGetAllSmsUseCase(smsRepository: SmsRepository): GetAllSms {
        return GetAllSms(smsRepository)
    }

    @Provides
    fun provideGetAllSmsByBankAccountNumberUseCase(smsRepository: SmsRepository): GetAllSmsByBankAccountNumber{
        return GetAllSmsByBankAccountNumber(smsRepository)
    }
    @Provides
    fun provideGetAllBankAccountNumberUseCase(smsRepository: SmsRepository): GetAllBankAccountNumber{
        return GetAllBankAccountNumber(smsRepository)
    }

    @Provides
    fun provideGetSingleSmsUseCase(smsRepository: SmsRepository): GetSingleSms {
        return GetSingleSms(smsRepository)
    }

    @Provides
    fun provideGetAllCategoryUseCase(categoryRepository: CategoryRepository): GetAllCategoryCount {
        return GetAllCategoryCount(categoryRepository)
    }

    @Provides
    fun provideCacheCategoryToDbUseCase(categoryRepository: CategoryRepository): CacheCategoryToDb {
        return CacheCategoryToDb(categoryRepository)
    }

    @Provides
    fun provideGetAllCategoryListUseCase(categoryRepository: CategoryRepository): GetAllCategoryList {
        return GetAllCategoryList(categoryRepository)
    }

    @Provides
    fun provideUpsertSmsUseCase(smsRepository: SmsRepository): UpsertSms {
        return UpsertSms(smsRepository)
    }

    @Provides
    fun provideSaveFirstOpenUseCase(preferenceHelper: PreferenceHelper): SaveFirstAppOpen{
        return SaveFirstAppOpen(preferenceHelper)
    }

    @Provides
    fun provideGetFirstOpenUseCase(preferenceHelper: PreferenceHelper): GetFirstOpenApp{
        return GetFirstOpenApp(preferenceHelper)
    }


}