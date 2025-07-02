package com.example.exchangerates.ui.home.rates.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import com.example.exchangerates.ui.home.rates.state.RatesScreenState
import com.example.exchangerates.ui.home.rates.state.RatesUiModel

internal class RatesScreenPreviewParamsProvider : PreviewParameterProvider<RatesScreenState> {
    private val mockRates = listOf(
        RatesUiModel(
            base = CurrencySymbol("EUR"),
            symbol = CurrencySymbol("USD"),
            rate = 1.0,
            isFavorite = false,
        ),
        RatesUiModel(
            base = CurrencySymbol("EUR"),
            symbol = CurrencySymbol("EUR"),
            rate = 0.85,
            isFavorite = true,
        ),
        RatesUiModel(
            base = CurrencySymbol("EUR"),
            symbol = CurrencySymbol("GBP"),
            rate = 0.73,
            isFavorite = false,
        )
    )

    override val values = sequenceOf(
        RatesScreenState.Loading,
        RatesScreenState.Error,
        RatesScreenState.Data(
            baseCurrency = CurrencySymbol("USD"),
            rates = mockRates,
            availableCurrencies = emptyList()
        ),
        RatesScreenState.Data(
            baseCurrency = CurrencySymbol("USD"),
            rates = mockRates.take(1),
            availableCurrencies = emptyList()
        ),
        RatesScreenState.Data(
            baseCurrency = CurrencySymbol("USD"),
            rates = emptyList(),
            availableCurrencies = emptyList(),
            isError = true,
        ),
    )
}