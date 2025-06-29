package com.example.exchangerates.core.dispatchers

import com.example.exchangerates.core.dispatchers.qualifiers.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DispatchersModule {

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}