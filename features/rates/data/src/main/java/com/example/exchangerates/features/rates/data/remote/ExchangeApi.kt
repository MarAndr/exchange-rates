package com.example.exchangerates.features.rates.data.remote

import com.example.exchangerates.core.remote.Constants
import com.example.exchangerates.features.rates.data.remote.model.CurrencyListDto
import com.example.exchangerates.features.rates.data.remote.model.RateDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

internal interface ExchangeApi {

    @GET("exchangerates_data/latest")
    @Headers("apikey: ${Constants.API_KEY}") // todo: interceptor
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbolsList: String,
    ): RateDto

    @GET("exchangerates_data/symbols")
    @Headers("apikey: ${Constants.API_KEY}") // todo: interceptor
    suspend fun getCurrencyNamesList(): CurrencyListDto
}