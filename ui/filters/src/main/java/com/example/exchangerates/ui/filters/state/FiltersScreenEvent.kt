package com.example.exchangerates.ui.filters.state

import com.example.exchangerates.features.filters.entities.SortOption

sealed interface FiltersScreenEvent {
    data object OnBackClicked : FiltersScreenEvent
    data class OnApply(val sortOption: SortOption) : FiltersScreenEvent
    data class SortOptionSelected(val option: SortOption) : FiltersScreenEvent
}