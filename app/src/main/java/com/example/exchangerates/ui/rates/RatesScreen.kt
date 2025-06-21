package com.example.exchangerates.ui.rates

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.ui.common.theme.AppTheme
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            item {
                CurrencyCard("USD", 20.0, true) { }
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