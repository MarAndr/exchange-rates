package com.example.exchangerates.ui.rates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
private fun RatesScreen(
    screenState: RatesScreenState,
    onEvent: (RatesScreenEvent) -> Unit,
) {

}

@Composable
@PreviewLightDark
private fun RatesScreenPreview() = AppTheme {
    RatesScreen(
        screenState = RatesScreenState.Loading,
        onEvent = {},
    )
}