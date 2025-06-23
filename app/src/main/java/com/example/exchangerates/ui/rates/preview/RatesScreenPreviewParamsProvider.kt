package com.example.exchangerates.ui.rates.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.exchangerates.features.rates.api.model.RatesItem
import com.example.exchangerates.ui.rates.state.RatesScreenState

internal class RatesScreenPreviewParamsProvider: PreviewParameterProvider<RatesScreenState> {
    private val mockRates = listOf(
        RatesItem(
            symbol = "USD",
            rate = 1.0,
            isFavorite = true
        ),
        RatesItem(
            symbol = "EUR",
            rate = 0.85,
            isFavorite = false
        ),
        RatesItem(
            symbol = "GBP",
            rate = 0.73,
            isFavorite = true
        )
    )

    override val values = sequenceOf(
        RatesScreenState.Loading,
        RatesScreenState.Error,
        RatesScreenState.Success(
            baseCurrency = "USD",
            rates = mockRates,
            availableCurrencies = emptyList()
        ),
        RatesScreenState.Success(
            baseCurrency = "USD",
            rates = mockRates.take(1),
            availableCurrencies = emptyList()
        ),
    )
}