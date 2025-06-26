package com.example.exchangerates.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exchangerates.ui.common.navigation.Destination
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.filters.FiltersScreen
import com.example.exchangerates.ui.filters.SortOption
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
            var selectedSortOption by remember { mutableStateOf(SortOption.CodeAZ) }

            AppTheme {
                val navController = rememberNavController()

                LaunchedEffect(navController, navigationEventsProvider) {
                    navigationEventsProvider.navigationEvents.collect { navEvent ->
                        when (navEvent) {
                            NavigationEvent.Back -> navController.popBackStack()
                            is NavigationEvent.NavigateTo -> navController.navigate(navEvent.destination)
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = Destination.Home,
                ) {
                    composable<Destination.Home> {
                        HomeScreen(selectedSortOption = selectedSortOption)
                    }

                    composable<Destination.Filters> {
                        FiltersScreen(
                            // todo navigator
                            currentSortOption = selectedSortOption,
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onApplyClick = { selectedOption ->
                                selectedSortOption = selectedOption
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}