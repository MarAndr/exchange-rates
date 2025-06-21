package com.example.exchangerates.ui.rates

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.R
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.rates.state.CurrencyItem
import com.example.exchangerates.ui.rates.state.RatesScreenEvent
import com.example.exchangerates.ui.rates.state.RatesScreenState
import com.example.exchangerates.ui.rates.state.RatesViewModel

@Composable
fun RatesScreen(
    viewModel: RatesViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    RatesScreen(
        screenState = screenState,
        baseCurrency = "USD", //todo передать сохранённую базовую валюту
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RatesScreen(
    screenState: RatesScreenState,
    baseCurrency: String,
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(AppTheme.color.bg.header)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            CurrencySelector(
                baseCurrency = baseCurrency,
                onBaseCurrencyChanged = { onEvent(RatesScreenEvent.OnBaseCurrencyChanged(it)) },
                onFilterClick = { /* TODO */ },
            )

            Spacer(Modifier.height(12.dp))

            when (screenState) {
                is RatesScreenState.Loading -> {
                    // TODO: Add loading indicator
                    Text("Loading...")
                }

                is RatesScreenState.Success -> {
                    LazyColumn(
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
}

@Composable
@Preview(showBackground = true, apiLevel = 34)
private fun RatesScreenPreview() = AppTheme {
    RatesScreen(
        screenState = RatesScreenState.Loading,
        baseCurrency = "USD",
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
        baseCurrency = "USD",
        onEvent = {},
    )
}