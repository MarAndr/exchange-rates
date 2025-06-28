package com.example.exchangerates.ui.common.navigation

interface AppNavigator {
    fun navigateTo(destination: Destination)
    fun back()
    fun backWithResult(result: Any?)
}