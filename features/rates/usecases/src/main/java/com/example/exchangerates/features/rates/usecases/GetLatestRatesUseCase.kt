package com.example.exchangerates.features.rates.usecases

import com.example.exchangerates.features.rates.abstractions.RatesRepository
import javax.inject.Inject

class GetLatestRatesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository,
) {
    operator fun invoke(
        baseCurrency: String,
        targetCurrencies: List<String> = emptyList()
    ) = ratesRepository.getLatestRates(baseCurrency, targetCurrencies)
}