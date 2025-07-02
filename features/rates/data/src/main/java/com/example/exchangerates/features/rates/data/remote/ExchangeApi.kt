package com.example.exchangerates.features.rates.data.remote

import com.example.exchangerates.features.rates.data.remote.model.CurrencyListDto
import com.example.exchangerates.features.rates.data.remote.model.RateDto
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ExchangeApi {

    @GET("exchangerates_data/latest")
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbolsList: String,
    ): RateDto

    @GET("exchangerates_data/symbols")
    suspend fun getCurrencyNamesList(): CurrencyListDto
}