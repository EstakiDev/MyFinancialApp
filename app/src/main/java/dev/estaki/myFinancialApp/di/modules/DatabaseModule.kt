package dev.estaki.myFinancialApp.di.modules

import android.content.Context
import androidx.room.Room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.db.dbClass.AppDatabase
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "financial.db")
            .build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): SmsDao {
        return appDatabase.smsDao()
    }

}