@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.exchangerates.ui.home.rates.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.core.dispatchers.qualifiers.IoDispatcher
import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.usecases.AddFavoritePairUseCase
import com.example.exchangerates.features.favorites.usecases.GetFavoritePairsUseCase
import com.example.exchangerates.features.favorites.usecases.RemoveFavoritePairUseCase
import com.example.exchangerates.features.filters.entities.SortOption
import com.example.exchangerates.features.rates.usecases.GetCurrenciesListUseCase
import com.example.exchangerates.features.rates.usecases.GetLatestRatesUseCase
import com.example.exchangerates.ui.common.navigation.AppNavigator
import com.example.exchangerates.ui.common.navigation.Destination
import com.example.exchangerates.ui.common.state.RefreshLoadingState
import com.example.exchangerates.ui.common.state.refreshable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val getLatestRates: GetLatestRatesUseCase,
    private val getCurrenciesList: GetCurrenciesListUseCase,
    getFavoritePairs: GetFavoritePairsUseCase,
    private val addFavoritePair: AddFavoritePairUseCase,
    private val removeFavoritePair: RemoveFavoritePairUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val baseCurrency: MutableStateFlow<String?> = MutableStateFlow(null)
    private val sortOption: MutableStateFlow<SortOption> = MutableStateFlow(SortOption.CodeAZ)

    private val ratesRefreshable = refreshable {
        baseCurrency
            .filterNotNull()
            .flatMapLatest { baseCurrency ->
                getLatestRates(baseCurrency)
            }
            .onStart { emit(LoadingState.Loading) }
    }

    private val currenciesRefreshable = refreshable {
        getCurrenciesList()
            .onEach { state ->
                if (state is LoadingState.Success && baseCurrency.value == null) {
                    baseCurrency.value = state.data.firstOrNull()?.symbol
                }
            }
    }

    val screenState: StateFlow<RatesScreenState> = combine(
        baseCurrency,
        ratesRefreshable.flow,
        currenciesRefreshable.flow,
        getFavoritePairs(),
        sortOption,
    ) { baseCurrency, ratesState, currenciesState, favoritePairs, currentSortOption ->
        when (currenciesState) {
            RefreshLoadingState.Initial.Loading -> RatesScreenState.Loading
            RefreshLoadingState.Initial.Error -> RatesScreenState.Error
            is RefreshLoadingState.Data -> {
                when (ratesState) {
                    RefreshLoadingState.Initial.Loading -> RatesScreenState.Loading
                    RefreshLoadingState.Initial.Error -> RatesScreenState.Error
                    is RefreshLoadingState.Data -> {
                        val rates = if (ratesState.isError) {
                            emptyList() // when an error occurred while changing currency
                        } else {
                            ratesState.data.map { rate ->
                                val isFavorite = favoritePairs
                                    .find { it.baseCurrency == baseCurrency && it.targetCurrency == rate.symbol } != null
                                RatesUiModel(
                                    base = baseCurrency.orEmpty(),
                                    symbol = rate.symbol,
                                    rate = rate.rate,
                                    isFavorite = isFavorite,
                                )
                            }.let { ratesList ->
                                when (currentSortOption) {
                                    SortOption.CodeAZ -> ratesList.sortedBy { it.symbol }
                                    SortOption.CodeZA -> ratesList.sortedByDescending { it.symbol }
                                    SortOption.QuoteAsc -> ratesList.sortedBy { it.rate }
                                    SortOption.QuoteDesc -> ratesList.sortedByDescending { it.rate }
                                }
                            }
                        }

                        RatesScreenState.Data(
                            baseCurrency = baseCurrency.orEmpty(),
                            rates = rates,
                            availableCurrencies = currenciesState.data,
                            isRefreshing = ratesState.isLoading || currenciesState.isLoading,
                            isError = ratesState.isError,
                        )
                    }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = RatesScreenState.Loading
    )

    fun onEvent(event: RatesScreenEvent) {
        when (event) {
            is RatesScreenEvent.OnFavoriteClick -> viewModelScope.launch(ioDispatcher) {
                if (event.rate.isFavorite) {
                    removeFavoritePair(
                        baseCurrency = event.rate.base,
                        targetCurrency = event.rate.symbol
                    )
                } else {
                    val pair = FavoritePair(
                        baseCurrency = event.rate.base,
                        targetCurrency = event.rate.symbol,
                    )
                    addFavoritePair(pair)
                }
            }

            is RatesScreenEvent.OnRefresh -> {
                currenciesRefreshable.refresh()
                ratesRefreshable.refresh()
            }

            is RatesScreenEvent.OnBaseCurrencyChanged -> {
                baseCurrency.value = event.newBaseCurrency
            }

            RatesScreenEvent.OpenFilters -> {
                appNavigator.navigateTo(Destination.Filters(sortOption.value))
            }

            is RatesScreenEvent.OnSortOptionChanged -> {
                sortOption.value = event.sortOption
            }
        }
    }
}