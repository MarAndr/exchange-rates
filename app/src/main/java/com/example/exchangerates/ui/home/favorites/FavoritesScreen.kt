@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.exchangerates.ui.home.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.R
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.home.favorites.state.FavoritesScreenEvent
import com.example.exchangerates.ui.home.favorites.state.FavoritesScreenState
import com.example.exchangerates.ui.home.favorites.state.FavoritesViewModel
import com.example.exchangerates.ui.home.rates.RatesCard

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    FavoritesScreen(
        screenState = screenState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun FavoritesScreen(
    screenState: FavoritesScreenState,
    onEvent: (FavoritesScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.color.mainColors.textDefault,
                        text = stringResource(R.string.favorites_title),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.color.bg.header,
                    titleContentColor = AppTheme.color.mainColors.textDefault,
                ),
            )
        },
        containerColor = AppTheme.color.bg.default,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            when (screenState) {
                is FavoritesScreenState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = AppTheme.color.mainColors.primary
                        )
                    }
                }

                is FavoritesScreenState.Data -> {
                    Spacer(Modifier.height(16.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            items = screenState.favoriteRates,
                            key = { "${it.base}/${it.symbol}" }
                        ) { rate ->
                            RatesCard(
                                modifier = Modifier
                                    .animateItem(),
                                title = "${rate.base}/${rate.symbol}",
                                rate = rate.rate,
                                isFavorite = true,
                                onFavoriteClick = {
                                    onEvent(FavoritesScreenEvent.RemoveFromFavorites(rate))
                                }
                            )
                        }
                    }
                }

                is FavoritesScreenState.Error -> {
                    // TODO: Add error state
                    Text("Error")
                }
            }
        }
    }
}