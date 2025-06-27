package com.example.exchangerates.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exchangerates.ui.common.navigation.Destination
import com.example.exchangerates.ui.common.navigation.navigateUp
import com.example.exchangerates.ui.common.navigation.rememberBackResultHandler
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.filters.FiltersScreen
import com.example.exchangerates.ui.home.HomeScreen
import com.example.exchangerates.ui.main.navigation.NavigationEventsProvider
import com.example.exchangerates.ui.main.navigation.model.NavigationEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationEventsProvider: NavigationEventsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()

                LaunchedEffect(navController, navigationEventsProvider) {
                    navigationEventsProvider.navigationEvents.collect { navEvent ->
                        when (navEvent) {
                            NavigationEvent.Back -> navController.popBackStack()
                            is NavigationEvent.NavigateTo -> navController.navigate(navEvent.destination)
                            is NavigationEvent.BackWithResult -> navController.navigateUp(navEvent.result)
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = Destination.Home,
                ) {
                    composable<Destination.Home> {
                        HomeScreen(
                            resultHandler = rememberBackResultHandler(it),
                        )
                    }

                    composable<Destination.Filters> {
                        FiltersScreen()
                    }
                }
            }
        }
    }
}