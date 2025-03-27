package dev.estaki.myFinancialApp.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.estaki.domain.repo.reposities.BankAccountRepository
import dev.estaki.domain.repo.reposities.CategoryRepository
import dev.estaki.domain.repo.reposities.SmsRepository
import dev.estaki.domain.sharedPrefrence.PreferenceHelper
import dev.estaki.domain.usecases.CacheAllBankAccountToDb
import dev.estaki.domain.usecases.CacheCategoryToDb
import dev.estaki.domain.usecases.CacheSmsToDb
import dev.estaki.domain.usecases.DeleteBankCard
import dev.estaki.domain.usecases.GetAllBankAccountNumberFromTbSms
import dev.estaki.domain.usecases.GetAllBankCardFromTbBankCard
import dev.estaki.domain.usecases.GetAllCategoryCount
import dev.estaki.domain.usecases.GetAllCategoryList
import dev.estaki.domain.usecases.GetAllSms
import dev.estaki.domain.usecases.GetAllSmsByBankAccountNumber
import dev.estaki.domain.usecases.GetFirstOpenApp
import dev.estaki.domain.usecases.GetSingleBankAccount
import dev.estaki.domain.usecases.GetSingleSms
import dev.estaki.domain.usecases.SaveFirstAppOpen
import dev.estaki.domain.usecases.SetSmsWasSaw
import dev.estaki.domain.usecases.UpsertBankCard
import dev.estaki.domain.usecases.UpsertSms

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
    fun provideGetAllBankAccountNumberFromTbSmsUseCase(smsRepository: SmsRepository): GetAllBankAccountNumberFromTbSms{
        return GetAllBankAccountNumberFromTbSms(smsRepository)
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
    @Provides
    fun provideGetSingleBankAccount(bankAccountRepository: BankAccountRepository): GetSingleBankAccount{
        return GetSingleBankAccount(bankAccountRepository)
    }

    @Provides
    fun provideCacheAllBankAccountToDb(bankAccountRepository: BankAccountRepository): CacheAllBankAccountToDb{
        return CacheAllBankAccountToDb(bankAccountRepository)
    }

    @Provides
    fun provideUpsertBankCardToDb(bankAccountRepository: BankAccountRepository): UpsertBankCard{
        return UpsertBankCard(bankAccountRepository)
    }

    @Provides
    fun provideGetAllBankAccountNumberUseCase(bankAccountRepository: BankAccountRepository): GetAllBankCardFromTbBankCard{
        return GetAllBankCardFromTbBankCard(bankAccountRepository)
    }

    @Provides
    fun provideDeleteBankCardUseCase(bankAccountRepository: BankAccountRepository):DeleteBankCard{
        return DeleteBankCard(bankAccountRepository)
    }

    @Provides
    fun provideSetSmsWasSawUseCase(smsRepository: SmsRepository): SetSmsWasSaw{
        return SetSmsWasSaw(smsRepository)
    }
}