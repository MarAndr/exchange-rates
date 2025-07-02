@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.exchangerates.ui.home.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.ui.common.components.EmptyStateBox
import com.example.exchangerates.ui.common.components.loading.AppPullToRefreshBox
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.home.favorites.preview.FavoritesScreenPreviewParamsProvider
import com.example.exchangerates.ui.home.favorites.state.FavoritesScreenEvent
import com.example.exchangerates.ui.home.favorites.state.FavoritesScreenState
import com.example.exchangerates.ui.home.favorites.state.FavoritesViewModel
import com.example.exchangerates.ui.home.components.RatesCard

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
        contentWindowInsets = WindowInsets.safeDrawing
            .exclude(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)),
    ) { paddingValues ->
        AppPullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            isLoading = screenState.isLoading,
            onRefresh = { onEvent(FavoritesScreenEvent.OnRefresh) },
        ) {
            if (screenState.favoriteRates.isEmpty()) {
                EmptyStateBox()
            } else {
                FavoriteRatesList(
                    screenState = screenState,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun FavoriteRatesList(
    screenState: FavoritesScreenState,
    onEvent: (FavoritesScreenEvent) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
        )
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

@Composable
@Preview(showBackground = true, apiLevel = 34)
private fun FavoritesScreenPreview(
    @PreviewParameter(FavoritesScreenPreviewParamsProvider::class)
    state: FavoritesScreenState,
) = AppTheme {
    FavoritesScreen(
        screenState = state,
        onEvent = {},
    )
}