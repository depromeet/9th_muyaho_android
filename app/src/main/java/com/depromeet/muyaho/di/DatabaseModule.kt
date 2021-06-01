package com.depromeet.muyaho.di

import android.content.Context
import com.depromeet.muyaho.data.AppDatabase
import com.depromeet.muyaho.data.StockDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideStockDao(appDatabase: AppDatabase): StockDao {
        return appDatabase.stockDao()
    }
}