package com.example.exchangerates.features.rates.impl

import com.example.exchangerates.features.rates.impl.remote.ExchangeApi
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.favorites.api.FavoriteDataSource
import com.example.exchangerates.features.rates.api.RatesRemoteDataSource
import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.api.model.Currency
import com.example.exchangerates.features.rates.api.model.RatesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val ratesRemoteDataSource: RatesRemoteDataSource,
) : RatesRepository {
    override fun getLatestRates(baseCurrency: String) =
        ratesRemoteDataSource.getLatestRates(baseCurrency)

    override fun getCurrencyList(): Flow<LoadingState<List<Currency>>> =
        ratesRemoteDataSource.getCurrencyList()
}


