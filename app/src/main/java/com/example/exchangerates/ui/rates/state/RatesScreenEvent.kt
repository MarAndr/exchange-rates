package com.example.exchangerates.ui.rates.state

import com.example.exchangerates.domain.model.Symbol

sealed interface RatesScreenEvent {
    data class OnFavoriteClick(val symbol: Symbol) : RatesScreenEvent
    data object OnScreenLoad : RatesScreenEvent
    data object OnRefresh : RatesScreenEvent
}