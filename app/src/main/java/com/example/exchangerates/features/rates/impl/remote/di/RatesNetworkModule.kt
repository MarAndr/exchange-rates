package com.example.exchangerates.features.rates.impl.remote.di

import com.example.exchangerates.features.rates.impl.remote.ExchangeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RatesNetworkModule {

    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit) =
        retrofit.create<ExchangeApi>()
}