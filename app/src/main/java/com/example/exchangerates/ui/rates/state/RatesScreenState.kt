package com.example.exchangerates.ui.rates.state

sealed interface RatesScreenState {
    data object Loading : RatesScreenState
}