package com.example.exchangerates.ui.home.rates.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.exchangerates.ui.home.rates.state.RatesScreenState
import com.example.exchangerates.ui.home.rates.state.RatesUiModel

internal class RatesScreenPreviewParamsProvider : PreviewParameterProvider<RatesScreenState> {
    private val mockRates = listOf(
        RatesUiModel(
            base = "EUR",
            symbol = "USD",
            rate = 1.0,
            isFavorite = false,
        ),
        RatesUiModel(
            base = "EUR",
            symbol = "EUR",
            rate = 0.85,
            isFavorite = true,
        ),
        RatesUiModel(
            base = "EUR",
            symbol = "GBP",
            rate = 0.73,
            isFavorite = false,
        )
    )

    override val values = sequenceOf(
        RatesScreenState.Loading,
        RatesScreenState.Error,
        RatesScreenState.Data(
            baseCurrency = "USD",
            rates = mockRates,
            availableCurrencies = emptyList()
        ),
        RatesScreenState.Data(
            baseCurrency = "USD",
            rates = mockRates.take(1),
            availableCurrencies = emptyList()
        ),
        RatesScreenState.Data(
            baseCurrency = "USD",
            rates = emptyList(),
            availableCurrencies = emptyList(),
            isError = true,
        ),
    )
}