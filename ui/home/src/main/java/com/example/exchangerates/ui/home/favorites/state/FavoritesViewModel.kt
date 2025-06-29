package com.example.exchangerates.ui.home.favorites.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.core.dispatchers.qualifiers.IoDispatcher
import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.usecases.GetFavoritePairsUseCase
import com.example.exchangerates.features.favorites.usecases.RemoveFavoritePairUseCase
import com.example.exchangerates.features.rates.entities.RatesItem
import com.example.exchangerates.features.rates.usecases.GetLatestRatesUseCase
import com.example.exchangerates.ui.common.state.RefreshLoadingState
import com.example.exchangerates.ui.common.state.refreshable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getLatestRates: GetLatestRatesUseCase,
    getFavoritePairs: GetFavoritePairsUseCase,
    private val removeFavoritePair: RemoveFavoritePairUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val favoritesRefreshable = refreshable {
        getFavoritePairs()
            .flatMapLatest {
                val baseGrouped = it.groupBy { it.baseCurrency }

                if (it.isNotEmpty()) {
                    fetchRates(baseGrouped)
                } else {
                    flowOf(LoadingState.Success(emptyList()))
                }
            }
    }

    val screenState = favoritesRefreshable.flow
        .map { ratesItemsState ->
            when (ratesItemsState) {
                is RefreshLoadingState.Data -> {
                    FavoritesScreenState.Data(
                        favoriteRates = ratesItemsState.data,
                        isRefreshing = ratesItemsState.isLoading,
                    )
                }

                RefreshLoadingState.Initial.Error -> FavoritesScreenState.Error
                RefreshLoadingState.Initial.Loading -> FavoritesScreenState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FavoritesScreenState.Loading,
        )

    private fun fetchRates(baseGrouped: Map<String, List<FavoritePair>>) =
        combine(baseGrouped.map { (base, targets) ->
            getLatestRates(base, targets.map { it.targetCurrency })
        }) { loadingStates ->
            val errors = loadingStates.filterIsInstance<LoadingState.Error>()
            val loadings = loadingStates.filterIsInstance<LoadingState.Loading>()
            val successes =
                loadingStates.filterIsInstance<LoadingState.Success<List<RatesItem>>>()
            when {
                errors.isNotEmpty() -> LoadingState.Error
                loadings.isNotEmpty() -> LoadingState.Loading
                else -> {
                    val rates: List<RatesItem> = successes
                        .fold<LoadingState.Success<List<RatesItem>>, List<RatesItem>>(emptyList()) { list, success ->
                            list + success.data
                        }
                        .sortedBy { "${it.base}/${it.symbol}" } // alphabetic
                    LoadingState.Success(rates)
                }
            }
        }

    fun onEvent(event: FavoritesScreenEvent) {
        when (event) {
            is FavoritesScreenEvent.RemoveFromFavorites -> viewModelScope.launch(ioDispatcher) {
                removeFavoritePair(
                    baseCurrency = event.ratesItem.base,
                    targetCurrency = event.ratesItem.symbol,
                )
            }

            FavoritesScreenEvent.OnRefresh -> favoritesRefreshable.refresh()
        }
    }
}