package com.example.exchangerates.features.rates.data.remote

import com.example.exchangerates.core.remote.calls.apiCall
import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.RatesItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RatesRemoteDataSourceImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
) : RatesRemoteDataSource {

    override fun getLatestRates(
        baseCurrency: String,
        targetCurrencies: List<String>,
    ) = apiCall(
        mapper = { rates ->
            rates.rates.map { (symbol, rate) ->
                RatesItem(
                    base = rates.base.orEmpty(),
                    symbol = symbol,
                    rate = rate,
                )
            }
        },
        call = {
            val targetCurrenciesList = targetCurrencies.joinToString(",")
            exchangeApi.getLatestCurrency(baseCurrency, targetCurrenciesList)
        },
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