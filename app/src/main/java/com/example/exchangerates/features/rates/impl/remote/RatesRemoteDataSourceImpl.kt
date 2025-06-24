package com.example.exchangerates.features.rates.impl.remote

import com.example.exchangerates.core.remote.calls.apiCall
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.rates.api.RatesRemoteDataSource
import com.example.exchangerates.features.rates.api.model.Currency
import com.example.exchangerates.features.rates.api.model.RatesItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RatesRemoteDataSourceImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
) : RatesRemoteDataSource {

    override fun getLatestRates(baseCurrency: String) = apiCall(
        mapper = { rates ->
            rates.rates.map { (symbol, rate) ->
                RatesItem(
                    symbol = symbol,
                    rate = rate,
                )
            }
        },
        call = { exchangeApi.getLatestCurrency(baseCurrency, null) },
    )

    override fun getCurrencyList(): Flow<LoadingState<List<Currency>>> = apiCall(
        mapper = { currencyList ->
            currencyList.symbols?.map { (symbol, name) ->
                Currency(
                    name = name,
                    symbol = symbol
                )
            } ?: emptyList()
        },
        call = { exchangeApi.getCurrencyNamesList() },
    )
}