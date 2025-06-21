package com.example.exchangerates.api

import com.example.exchangerates.api.model.NamesApiModel
import com.example.exchangerates.api.model.RatesApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @GET("exchangerates_data/latest")
    @Headers("apikey: ${Constants.API_KEY}")
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): RatesApiModel

    @GET("exchangerates_data/symbols")
    @Headers("apikey: ${Constants.API_KEY}")
    suspend fun getCurrencyNamesList(): NamesApiModel
}