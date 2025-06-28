package com.example.exchangerates.features.rates.impl.remote

import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.api.model.Currency
import com.example.exchangerates.features.rates.api.model.RatesItem
import kotlinx.coroutines.flow.Flow

internal interface RatesRemoteDataSource {
    fun getLatestRates(baseCurrency: String, targetCurrencies: List<String>): Flow<LoadingState<List<RatesItem>>>
    fun getCurrencyList(): Flow<LoadingState<List<Currency>>>
}