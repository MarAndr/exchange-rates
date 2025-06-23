package com.example.exchangerates.core.db.di

import android.app.Application
import androidx.room.Room
import com.example.exchangerates.core.db.CurrencyDatabase
import com.example.exchangerates.features.rates.impl.db.CurrenciesListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            CurrencyDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyListDao(db: CurrencyDatabase): CurrenciesListDao {
        return db.currenciesListDao()
    }
}
