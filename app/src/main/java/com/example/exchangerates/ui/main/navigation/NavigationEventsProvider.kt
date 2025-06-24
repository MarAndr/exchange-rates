package com.example.exchangerates.ui.main.navigation

import com.example.exchangerates.ui.main.navigation.model.NavigationEvent
import kotlinx.coroutines.flow.Flow

interface NavigationEventsProvider {
    val navigationEvents: Flow<NavigationEvent>
}