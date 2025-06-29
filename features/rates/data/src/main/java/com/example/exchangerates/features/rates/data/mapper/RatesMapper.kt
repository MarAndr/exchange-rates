package com.example.exchangerates.features.rates.data.mapper

import com.example.exchangerates.features.rates.data.remote.model.CurrencyListDto
import com.example.exchangerates.features.rates.data.remote.model.RateDto
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.RatesItem

internal object RatesMapper {
    
    fun mapRateDtoToRatesItems(rateDto: RateDto): List<RatesItem> {
        return rateDto.rates.map { (symbol, rate) ->
            RatesItem(
                base = rateDto.base.orEmpty(),
                symbol = symbol,
                rate = rate,
            )
        }
    }
    
    fun mapCurrencyListDtoToCurrencies(currencyListDto: CurrencyListDto): List<Currency> {
        return currencyListDto.symbols?.map { (symbol, name) ->
            Currency(
                name = name,
                symbol = symbol
            )
        } ?: emptyList()
    }
} 