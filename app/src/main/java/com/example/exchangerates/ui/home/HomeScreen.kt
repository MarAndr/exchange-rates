package com.example.exchangerates.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.R
import com.example.exchangerates.ui.common.navigation.BackResultHandler
import com.example.exchangerates.ui.common.navigation.PreviewBackResultHandler
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.filters.SortOption
import com.example.exchangerates.ui.home.favorites.FavoritesScreen
import com.example.exchangerates.ui.home.rates.RatesScreen
import com.example.exchangerates.ui.home.state.HomeScreenEvent
import com.example.exchangerates.ui.home.state.HomeScreenState
import com.example.exchangerates.ui.home.state.HomeTab
import com.example.exchangerates.ui.home.state.HomeTab.Favorites
import com.example.exchangerates.ui.home.state.HomeTab.Rates
import com.example.exchangerates.ui.home.state.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    resultHandler: BackResultHandler<SortOption>,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    HomeScreen(
        screenState = screenState,
        resultHandler = resultHandler,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun HomeScreen(
    screenState: HomeScreenState,
    resultHandler: BackResultHandler<SortOption>,
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
                Rates -> RatesScreen(resultHandler = resultHandler)
                Favorites -> FavoritesScreen()
            }
        }
        HorizontalDivider(color = AppTheme.color.mainColors.outline)
        NavigationBar(
            containerColor = AppTheme.color.bg.default,
        ) {
            val selectedTab = screenState.selectedTab
            HomeTab.entries.forEach { tab ->
                NavigationBarItem(
                    selected = selectedTab == tab,
                    colors = NavigationBarItemColors(
                        selectedIconColor = AppTheme.color.mainColors.primary,
                        selectedTextColor = AppTheme.color.mainColors.textDefault,
                        selectedIndicatorColor = AppTheme.color.mainColors.lightPrimary,
                        unselectedIconColor = AppTheme.color.mainColors.secondary,
                        unselectedTextColor = AppTheme.color.mainColors.secondary,
                        disabledIconColor = AppTheme.color.mainColors.secondary,
                        disabledTextColor = AppTheme.color.mainColors.secondary
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(tab.toIconRes()),
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(tab.name)
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
        screenState = HomeScreenState(
            Rates,
        ),
        resultHandler = PreviewBackResultHandler(),
        onEvent = {},
    )
}