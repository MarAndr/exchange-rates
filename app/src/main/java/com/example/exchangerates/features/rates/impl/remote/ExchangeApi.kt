package com.example.exchangerates.features.rates.impl.remote

import com.example.exchangerates.core.remote.Constants
import com.example.exchangerates.features.rates.impl.remote.model.NamesApiModel
import com.example.exchangerates.features.rates.impl.remote.model.RateDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @GET("exchangerates_data/latest")
    @Headers("apikey: ${Constants.API_KEY}") // todo: interceptor
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbols: String?,
    ): RateDto

    @GET("exchangerates_data/symbols")
    @Headers("apikey: ${Constants.API_KEY}") // todo: interceptor
    suspend fun getCurrencyNamesList(): NamesApiModel
}