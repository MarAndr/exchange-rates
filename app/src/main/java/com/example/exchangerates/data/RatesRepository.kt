package com.example.exchangerates.data

import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.ui.rates.state.RatesItem
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    suspend fun getLatestRates(baseCurrency: String): Flow<LoadingState<List<RatesItem>>>
    // suspend fun getCurrencyList(): Flow<LoadingState<List<CurrencyItem>>> // todo
}
