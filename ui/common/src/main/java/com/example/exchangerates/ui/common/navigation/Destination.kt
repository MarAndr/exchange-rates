package com.example.exchangerates.ui.common.navigation

import com.example.exchangerates.features.filters.entities.SortOption
import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Home : Destination

    @Serializable
    data class Filters(
        val selectedSorting: SortOption = SortOption.CodeAZ,
    ) : Destination
}