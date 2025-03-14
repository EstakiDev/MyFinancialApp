package dev.estaki.myFinancialApp.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.estaki.data.sharedPrefrence.SharedPreferencesHelperImpl
import dev.estaki.domain.sharedPrefrence.PreferenceHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("Monify_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceHelper(sharedPreferences: SharedPreferences): PreferenceHelper{
        return SharedPreferencesHelperImpl(sharedPreferences = sharedPreferences)
    }
}