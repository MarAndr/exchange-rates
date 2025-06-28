package com.example.exchangerates.ui.home.rates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.ui.common.navigation.BackResultEffect
import com.example.exchangerates.ui.common.navigation.BackResultHandler
import com.example.exchangerates.features.filters.api.model.SortOption
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.home.rates.preview.RatesScreenPreviewParamsProvider
import com.example.exchangerates.ui.home.rates.state.RatesScreenEvent
import com.example.exchangerates.ui.home.rates.state.RatesScreenState
import com.example.exchangerates.ui.home.rates.state.RatesViewModel

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
        val refreshState = rememberPullToRefreshState()
        val isRefreshing = screenState is RatesScreenState.Success && screenState.isRefreshing
        PullToRefreshBox(
            modifier = Modifier
                .padding(paddingValues),
            state = refreshState,
            isRefreshing = isRefreshing,
            onRefresh = { onEvent(RatesScreenEvent.OnRefresh) },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    state = refreshState,
                    containerColor = AppTheme.color.bg.default,
                    color = AppTheme.color.mainColors.primary,
                )
            },
        ) {
            Column {
                when (screenState) {
                    is RatesScreenState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = AppTheme.color.mainColors.primary
                            )
                        }
                    }

                    is RatesScreenState.Success -> {
                        CurrencySelector(
                            modifier = Modifier
                                .background(AppTheme.color.bg.header)
                                .padding(top = 8.dp, bottom = 12.dp)
                                .padding(horizontal = 16.dp),
                            baseCurrency = screenState.baseCurrency,
                            availableCurrencies = screenState.availableCurrencies,
                            onBaseCurrencyChanged = {
                                onEvent(
                                    RatesScreenEvent.OnBaseCurrencyChanged(
                                        it
                                    )
                                )
                            },
                            onFilterClick = {
                                onEvent(RatesScreenEvent.OpenFilters)
                            },
                        )

                        Spacer(Modifier.height(12.dp))
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            items(
                                items = screenState.rates,
                                key = { it.symbol }
                            ) { rates ->
                                RatesCard(
                                    modifier = Modifier
                                        .animateItem(),
                                    title = rates.symbol,
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

                    is RatesScreenState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.error_occurred_try_again),
                                    fontSize = 16.sp,
                                    color = AppTheme.color.mainColors.textSecondary,
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    colors = ButtonColors(
                                        containerColor = AppTheme.color.mainColors.primary,
                                        contentColor = AppTheme.color.mainColors.onPrimary,
                                        disabledContainerColor = AppTheme.color.mainColors.textDefault,
                                        disabledContentColor = AppTheme.color.mainColors.secondary
                                    ),
                                    onClick = { onEvent(RatesScreenEvent.OnRefresh) }
                                ) {
                                    Text(text = stringResource(R.string.refresh))
                                }
                            }
                        }
                    }
                }
            }
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