package com.example.exchangerates.ui.home.rates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.exchangerates.features.filters.entities.SortOption
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.ui.common.components.ErrorBox
import com.example.exchangerates.ui.common.components.loading.AppPullToRefreshBox
import com.example.exchangerates.ui.common.navigation.BackResultEffect
import com.example.exchangerates.ui.common.navigation.BackResultHandler
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.home.components.RatesCard
import com.example.exchangerates.ui.home.rates.components.CurrencySelector
import com.example.exchangerates.ui.home.rates.preview.RatesScreenPreviewParamsProvider
import com.example.exchangerates.ui.home.rates.state.RatesScreenEvent
import com.example.exchangerates.ui.home.rates.state.RatesScreenState
import com.example.exchangerates.ui.home.rates.state.RatesViewModel
import com.example.exchangerates.ui.home.rates.state.isLoading

@Composable
fun RatesScreen(
    viewModel: RatesViewModel = hiltViewModel(),
    resultHandler: BackResultHandler<SortOption>,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    BackResultEffect(resultHandler) {
        viewModel.onEvent(RatesScreenEvent.OnSortOptionChanged(sortOption = it))
    }

    RatesScreen(
        screenState = screenState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RatesScreen(
    screenState: RatesScreenState,
    onEvent: (RatesScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.color.mainColors.textDefault,
                        text = stringResource(R.string.currencies_title),
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
                .padding(paddingValues)
                .fillMaxSize(),
            isLoading = screenState.isLoading,
            onRefresh = { onEvent(RatesScreenEvent.OnRefresh) },
        ) {
            when (screenState) {
                is RatesScreenState.Loading -> {
                    // showing PtR loader
                }

                is RatesScreenState.Data -> {
                    RatesScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        screenState = screenState,
                        onEvent = onEvent
                    )
                }

                is RatesScreenState.Error -> {
                    ErrorBox {
                        onEvent(RatesScreenEvent.OnRefresh)
                    }
                }
            }
        }
    }
}

@Composable
private fun RatesScreenContent(
    modifier: Modifier = Modifier,
    screenState: RatesScreenState.Data,
    onEvent: (RatesScreenEvent) -> Unit
) {
    val lazyColumnState = rememberLazyListState()

    LaunchedEffect(screenState.baseCurrency, screenState.sortOption) {
        if (screenState.rates.isNotEmpty()) {
            lazyColumnState.animateScrollToItem(0)
        }
    }

    Column(modifier = modifier) {
        CurrencySelector(
            modifier = Modifier
                .background(AppTheme.color.bg.header)
                .padding(top = 8.dp, bottom = 12.dp)
                .padding(horizontal = 16.dp),
            baseCurrency = screenState.baseCurrency,
            availableCurrencies = screenState.availableCurrencies,
            onBaseCurrencyChanged = {
                onEvent(
                    RatesScreenEvent.OnBaseCurrencyChanged(it)
                )
            },
            onFilterClick = {
                onEvent(RatesScreenEvent.OpenFilters)
            },
        )

        if (screenState.rates.isEmpty() && screenState.isError) {
            ErrorBox {
                onEvent(RatesScreenEvent.OnRefresh)
            }
        } else {
            RatesList(
                lazyColumnState = lazyColumnState,
                screenState = screenState,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun RatesList(
    lazyColumnState: LazyListState,
    screenState: RatesScreenState.Data,
    onEvent: (RatesScreenEvent) -> Unit
) {
    LazyColumn(
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            vertical = 12.dp,
            horizontal = 16.dp
        ),
    ) {
        items(
            items = screenState.rates,
            key = { it.symbol.value }
        ) { rates ->
            RatesCard(
                modifier = Modifier
                    .animateItem(),
                title = rates.symbol.value,
                rate = rates.rate,
                isFavorite = rates.isFavorite,
                onFavoriteClick = {
                    onEvent(
                        RatesScreenEvent.OnFavoriteClick(
                            rate = rates,
                            wasFavorite = rates.isFavorite,
                        )
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 34)
private fun RatesScreenSuccessPreview(
    @PreviewParameter(RatesScreenPreviewParamsProvider::class)
    state: RatesScreenState,
) = AppTheme {
    RatesScreen(
        screenState = state,
        onEvent = {},
    )
}