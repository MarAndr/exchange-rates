package com.example.exchangerates.ui.main.rates.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.exchangerates.ui.main.rates.state.RatesScreenState
import com.example.exchangerates.ui.main.rates.state.RatesUiModel

internal class RatesScreenPreviewParamsProvider : PreviewParameterProvider<RatesScreenState> {
    private val mockRates = listOf(
        RatesUiModel(
            symbol = "USD",
            rate = 1.0,
            favoriteId = 1,
        ),
        RatesUiModel(
            symbol = "EUR",
            rate = 0.85,
            favoriteId = null,
        ),
        RatesUiModel(
            symbol = "GBP",
            rate = 0.73,
            favoriteId = 2,
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