package com.example.exchangerates.ui.home.state

data class HomeScreenState(
    val selectedTab: HomeTab,
)

enum class HomeTab {
    Currencies,
    Favorites,
}
