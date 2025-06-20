package com.example.exchangerates.ui.common.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Main : Destination

    @Serializable
    data object Filters : Destination
}