package com.example.exchangerates.features.rates.data.remote

import com.example.exchangerates.core.remote.calls.apiCall
import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.data.mapper.RatesMapper
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import com.example.exchangerates.features.rates.entities.RatesItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RatesRemoteDataSourceImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
) : RatesRemoteDataSource {

    override fun getLatestRates(
        baseCurrency: CurrencySymbol,
        targetCurrencies: List<CurrencySymbol>,
    ) = apiCall(
        mapper = { rates ->
            RatesMapper.mapRateDtoToRatesItems(rates)
        },
        call = {
            val targetCurrenciesList = targetCurrencies.joinToString(",") { it.value }
            exchangeApi.getLatestCurrency(baseCurrency.value, targetCurrenciesList)
        },
    )

    override fun getCurrencyList(): Flow<LoadingState<List<Currency>>> = apiCall(
        mapper = { currencyList ->
            RatesMapper.mapCurrencyListDtoToCurrencies(currencyList)
        },
        call = { exchangeApi.getCurrencyNamesList() },
    )
}