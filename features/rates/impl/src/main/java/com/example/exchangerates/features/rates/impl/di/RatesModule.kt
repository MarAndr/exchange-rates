package com.example.exchangerates.features.rates.impl.di

import com.example.exchangerates.features.rates.impl.remote.RatesRemoteDataSource
import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.impl.RatesRepositoryImpl
import com.example.exchangerates.features.rates.impl.remote.RatesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RatesModule {

    @Binds
    @Singleton
    fun provideRatesRepository(impl: RatesRepositoryImpl): RatesRepository

    @Binds
    @Singleton
    fun provideRatesDataSource(impl: RatesRemoteDataSourceImpl): RatesRemoteDataSource
}