package com.example.exchangerates.ui.rates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.rates.state.CurrencyItem
import com.example.exchangerates.ui.rates.state.RatesScreenEvent
import com.example.exchangerates.ui.rates.state.RatesScreenState
import com.example.exchangerates.ui.rates.state.RatesViewModel
import androidx.compose.ui.unit.dp

@Composable
fun RatesScreen(
    viewModel: RatesViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    RatesScreen(
        screenState = screenState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RatesScreen(
    screenState: RatesScreenState,
    onEvent: (RatesScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Currencies",
                    )
                },
            )
        },
    ) { paddingValues ->
        when (screenState) {
            is RatesScreenState.Loading -> {
                // TODO: Add loading indicator
                Text("Loading...")
            }
            is RatesScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = screenState.currencies,
                        key = { it.symbol }
                    ) { currency ->
                        CurrencyCard(
                            currencyCode = currency.symbol,
                            rate = currency.rate,
                            isFavorite = currency.isFavorite,
                            onFavoriteClick = {
                                onEvent(RatesScreenEvent.OnFavoriteClick(currency.symbol))
                            }
                        )
                    }
                }
            }
            is RatesScreenState.Error -> {
                // TODO: Add error state
                Text("Error: ${screenState.message}")
            }
        }
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 34)
private fun RatesScreenPreview() = AppTheme {
    RatesScreen(
        screenState = RatesScreenState.Loading,
        onEvent = {},
    )
}

@Composable
@Preview(name = "Success State", showBackground = true, apiLevel = 34)
private fun RatesScreenSuccessPreview() = AppTheme {
    val mockCurrencies = listOf(
        CurrencyItem(
            symbol = "USD",
            rate = 1.0,
            isFavorite = true
        ),
        CurrencyItem(
            symbol = "EUR",
            rate = 0.85,
            isFavorite = false
        ),
        CurrencyItem(
            symbol = "GBP",
            rate = 0.73,
            isFavorite = true
        )
    )
    
    RatesScreen(
        screenState = RatesScreenState.Success(mockCurrencies),
        onEvent = {},
    )
}