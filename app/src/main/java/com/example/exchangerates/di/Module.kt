package com.example.exchangerates.di

import android.app.Application
import androidx.room.Room
import com.example.exchangerates.api.ExchangeApi
import com.example.exchangerates.data.RatesRepository
import com.example.exchangerates.data.RatesRepositoryImpl
import com.example.exchangerates.db.CurrenciesListDao
import com.example.exchangeratestestapppublic.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {
    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): ExchangeApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideRatesRepository(
        exchangeApi: ExchangeApi
    ): RatesRepository {
        return RatesRepositoryImpl(exchangeApi)
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
//            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
//                setLevel(
//                    HttpLoggingInterceptor.Level.BODY
//                )
//            })
            .build()
    }
}