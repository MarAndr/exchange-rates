package com.example.exchangerates.ui.main.navigation

import com.example.exchangerates.ui.common.navigation.AppNavigator
import com.example.exchangerates.ui.common.navigation.Destination
import com.example.exchangerates.ui.main.navigation.model.NavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorImpl @Inject constructor() : AppNavigator, NavigationEventsProvider {
    private val _channel = Channel<NavigationEvent>()
    override val navigationEvents = _channel.receiveAsFlow()

    override fun navigateTo(destination: Destination) {
        _channel.trySend(NavigationEvent.NavigateTo(destination))
    }

    override fun back() {
        _channel.trySend(NavigationEvent.Back)
    }
}