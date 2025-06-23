package com.example.exchangerates.features.rates.impl.di

import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.impl.RatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RatesDomainModule {

    @Binds
    @Singleton
    fun provideExchangeRepository(impl: RatesRepositoryImpl): RatesRepository
}