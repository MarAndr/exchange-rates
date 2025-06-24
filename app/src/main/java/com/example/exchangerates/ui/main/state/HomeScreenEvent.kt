package com.example.exchangerates.ui.main.state

sealed interface HomeScreenEvent {
    data class TabSelected(val tab: HomeTab) : HomeScreenEvent
}