package com.example.exchangerates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exchangerates.ui.common.navigation.Destination
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.filters.FiltersScreen
import com.example.exchangerates.ui.main.HomeScreen
import com.example.exchangerates.ui.main.rates.RatesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Destination.Home,
                ) {
                    composable<Destination.Home> {
                        HomeScreen()
                    }

                    composable<Destination.Filters> {
                        FiltersScreen(
                            // todo navigator
                            onBackClick = {
                                navController.popBackStack()
                            },
                            onOptionsSelected = {} //todo
                        )
                    }
                }
            }
        }
    }
}