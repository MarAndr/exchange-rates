package com.example.exchangerates.features.rates.usecases

import com.example.exchangerates.features.rates.abstractions.RatesRepository
import javax.inject.Inject

class GetCurrenciesListUseCase @Inject constructor(
    private val ratesRepository: RatesRepository,
) {
    operator fun invoke() =
        ratesRepository.getCurrencyList()
}