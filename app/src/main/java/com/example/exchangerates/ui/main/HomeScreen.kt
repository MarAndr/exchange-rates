package com.example.exchangerates.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.R
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.main.favorites.FavoritesScreen
import com.example.exchangerates.ui.main.rates.RatesScreen
import com.example.exchangerates.ui.main.state.HomeScreenEvent
import com.example.exchangerates.ui.main.state.HomeScreenState
import com.example.exchangerates.ui.main.state.HomeTab
import com.example.exchangerates.ui.main.state.HomeTab.Favorites
import com.example.exchangerates.ui.main.state.HomeTab.Rates
import com.example.exchangerates.ui.main.state.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    HomeScreen(
        screenState = screenState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun HomeScreen(
    screenState: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    val state = rememberPagerState { HomeTab.entries.size }

    LaunchedEffect(screenState.selectedTab) {
        val pageIndex = screenState.selectedTab.ordinal
        state.animateScrollToPage(pageIndex)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        HorizontalPager(
            modifier = Modifier
                .weight(1f),
            state = state,
            beyondViewportPageCount = 1,
            userScrollEnabled = false,
        ) { page ->
            val tab = HomeTab.entries[page]
            when (tab) {
                Rates -> RatesScreen()
                Favorites -> FavoritesScreen()
            }
        }

        NavigationBar(
            containerColor = AppTheme.color.bg.default,
        ) {
            val selectedTab = screenState.selectedTab
            HomeTab.entries.forEach { tab ->
                NavigationBarItem(
                    selected = selectedTab == tab,
                    icon = {
                        Icon(
                            painter = painterResource(tab.toIconRes()),
                            contentDescription = null,
                        )
                    },
                    onClick = {
                        onEvent(HomeScreenEvent.TabSelected(tab))
                    },
                )
            }
        }
    }
}

private fun HomeTab.toIconRes() = when (this) {
    Rates -> R.drawable.ic_currencies_menu
    Favorites -> R.drawable.ic_favorite_menu
}

@Composable
@PreviewLightDark
private fun PreviewHomeScreen() = AppTheme {
    HomeScreen(
        screenState = HomeScreenState(Rates),
        onEvent = {},
    )
}