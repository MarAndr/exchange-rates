package com.example.exchangerates.data

import com.example.exchangerates.api.ExchangeApi
import com.example.exchangerates.ui.rates.state.CurrencyItem
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val exchangeApi: ExchangeApi,
): RatesRepository {
    override suspend fun getLatestCurrencies(baseCurrency: String): List<CurrencyItem> {
        val rateDto = exchangeApi.getLatestCurrency(baseCurrency, null)
        val rates = rateDto.rates
        return rates?.map { (symbol, rate) ->
            CurrencyItem(
                symbol = symbol,
                rate = rate,
                isFavorite = false, //todo
            )
        }.orEmpty()
    }
}


