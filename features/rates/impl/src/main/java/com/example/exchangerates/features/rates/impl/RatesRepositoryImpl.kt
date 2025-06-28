package com.example.exchangerates.features.rates.impl

import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.impl.remote.RatesRemoteDataSource
import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.api.model.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RatesRepositoryImpl @Inject constructor(
    private val ratesRemoteDataSource: RatesRemoteDataSource,
) : RatesRepository {
    override fun getLatestRates(baseCurrency: String, targetCurrencies: List<String>) =
        ratesRemoteDataSource.getLatestRates(baseCurrency, targetCurrencies)

    override fun getCurrencyList(): Flow<LoadingState<List<Currency>>> =
        ratesRemoteDataSource.getCurrencyList()
}


