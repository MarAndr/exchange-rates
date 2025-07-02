package com.example.exchangerates.features.rates.data.remote

import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import com.example.exchangerates.features.rates.entities.RatesItem
import kotlinx.coroutines.flow.Flow

internal interface RatesRemoteDataSource {
    fun getLatestRates(baseCurrency: CurrencySymbol, targetCurrencies: List<CurrencySymbol>): Flow<LoadingState<List<RatesItem>>>
    fun getCurrencyList(): Flow<LoadingState<List<Currency>>>
}