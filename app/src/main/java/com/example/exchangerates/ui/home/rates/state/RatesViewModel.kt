@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.exchangerates.ui.home.rates.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.api.model.FavoritePair
import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.ui.common.navigation.AppNavigator
import com.example.exchangerates.ui.common.navigation.Destination
import com.example.exchangerates.ui.common.state.RefreshLoadingState
import com.example.exchangerates.ui.common.state.refreshable
import com.example.exchangerates.ui.filters.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val ratesRepository: RatesRepository,
    private val favoriteRepository: FavoritePairsRepository,
) : ViewModel() {

    private val baseCurrency: MutableStateFlow<String?> = MutableStateFlow(null)
    private val sortOption: MutableStateFlow<SortOption> = MutableStateFlow(SortOption.CodeAZ)

    private val ratesRefreshable = refreshable {
        baseCurrency
            .filterNotNull()
            .flatMapLatest { baseCurrency ->
                ratesRepository.getLatestRates(baseCurrency)
            }
            .onStart { emit(LoadingState.Loading) }
    }

    private val currenciesRefreshable = refreshable {
        ratesRepository.getCurrencyList()
            .onEach { state ->
                if (state is LoadingState.Success) {
                    baseCurrency.value = state.data.firstOrNull()?.symbol
                }
            }
    }

    val screenState: StateFlow<RatesScreenState> = combine(
        baseCurrency,
        ratesRefreshable.flow,
        currenciesRefreshable.flow,
        favoriteRepository.getPairs(),
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
                        val rates = ratesState.data.map { rate ->
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
                        
                        RatesScreenState.Success(
                            baseCurrency = baseCurrency.orEmpty(),
                            rates = rates,
                            availableCurrencies = currenciesState.data,
                            isRefreshing = ratesState.isLoading || currenciesState.isLoading,
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
            // todo provide dispatchers.io from DI
            is RatesScreenEvent.OnFavoriteClick -> viewModelScope.launch(Dispatchers.IO) {
                if (event.rate.isFavorite) {
                    favoriteRepository.removePair(
                        baseCurrency = event.rate.base,
                        targetCurrency = event.rate.symbol
                    )
                } else {
                    val pair = FavoritePair(
                        baseCurrency = event.rate.base,
                        targetCurrency = event.rate.symbol,
                    )
                    favoriteRepository.addPair(pair)
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