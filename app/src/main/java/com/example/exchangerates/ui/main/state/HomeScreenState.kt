package com.example.exchangerates.ui.main.state

data class HomeScreenState(
    val selectedTab: HomeTab,
)

enum class HomeTab {
    Rates,
    Favorites,
}
