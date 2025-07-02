package com.example.exchangerates.features.rates.usecases

import com.example.exchangerates.features.rates.abstractions.RatesRepository
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import javax.inject.Inject

class GetLatestRatesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository,
) {
    operator fun invoke(
        baseCurrency: CurrencySymbol,
        targetCurrencies: List<CurrencySymbol> = emptyList()
    ) = ratesRepository.getLatestRates(baseCurrency, targetCurrencies)
}