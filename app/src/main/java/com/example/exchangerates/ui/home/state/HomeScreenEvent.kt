package com.example.exchangerates.ui.home.state

sealed interface HomeScreenEvent {
    data class TabSelected(val tab: HomeTab) : HomeScreenEvent
}