package com.example.exchangerates.ui.main.navigation.model

import com.example.exchangerates.ui.common.navigation.Destination

sealed interface NavigationEvent {
    data object Back : NavigationEvent

    data class BackWithResult(val result: Any?) : NavigationEvent

    data class NavigateTo(val destination: Destination) : NavigationEvent
}