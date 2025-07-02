package com.example.exchangerates.features.rates.data

import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.data.remote.RatesRemoteDataSource
import com.example.exchangerates.features.rates.abstractions.RatesRepository
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RatesRepositoryImpl @Inject constructor(
    private val ratesRemoteDataSource: RatesRemoteDataSource,
) : RatesRepository {
    override fun getLatestRates(baseCurrency: CurrencySymbol, targetCurrencies: List<CurrencySymbol>) =
        ratesRemoteDataSource.getLatestRates(baseCurrency, targetCurrencies)

    override fun getCurrencyList(): Flow<LoadingState<List<Currency>>> =
        ratesRemoteDataSource.getCurrencyList()
}


