package com.example.exchangerates.core.remote.di

import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.impl.RatesRepositoryImpl
import com.example.exchangerates.features.rates.impl.remote.ExchangeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {
    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
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