package com.example.exchangerates.ui.home.favorites.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.core.dispatchers.qualifiers.IoDispatcher
import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.favorites.entities.FavoritePair
import com.example.exchangerates.features.favorites.usecases.GetFavoritePairsUseCase
import com.example.exchangerates.features.favorites.usecases.RemoveFavoritePairUseCase
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import com.example.exchangerates.features.rates.entities.RatesItem
import com.example.exchangerates.features.rates.usecases.GetLatestRatesUseCase
import com.example.exchangerates.ui.common.state.RefreshLoadingState
import com.example.exchangerates.ui.common.state.isLoading
import com.example.exchangerates.ui.common.state.refreshable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            .flatMapLatest { favoritePairs ->
                val baseGrouped = favoritePairs.groupBy { it.baseCurrency }

                if (favoritePairs.isNotEmpty()) {
                    fetchRates(baseGrouped)
                } else {
                    flowOf(LoadingState.Success(emptyList()))
                }
            }
    }

    val screenState = combine(
        getFavoritePairs(),
        favoritesRefreshable.flow,
    ) { favoritePairs, ratesItemsState ->
        val favoriteRates = getFavoriteRates(
            ratesItemsState = ratesItemsState,
            favoritePairs = favoritePairs,
        )
        FavoritesScreenState(
            favoriteRates = favoriteRates,
            isLoading = ratesItemsState.isLoading(),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = FavoritesScreenState(favoriteRates = emptyList()),
    )

    private suspend fun getFavoriteRates(
        ratesItemsState: RefreshLoadingState<List<RatesItem>>,
        favoritePairs: List<FavoritePair>
    ): List<FavoriteRateUiModel> = withContext(ioDispatcher) {
        val ratesItems = ratesItemsState as? RefreshLoadingState.Data
        val favoriteRates = favoritePairs
            .sortedBy { "${it.baseCurrency}/${it.targetCurrency}" } // alphabetic
            .map {
                FavoriteRateUiModel(
                    base = it.baseCurrency,
                    symbol = it.targetCurrency,
                    rate = null,
                )
            }.map { favoriteUiModel ->
                if (ratesItems == null) {
                    favoriteUiModel
                } else {
                    val rate = ratesItems.data
                        .find { it.base == favoriteUiModel.base && it.symbol == favoriteUiModel.symbol }
                        ?.rate
                    favoriteUiModel.copy(
                        rate = rate,
                    )
                }
            }
        return@withContext favoriteRates
    }

    private fun fetchRates(baseGrouped: Map<CurrencySymbol, List<FavoritePair>>) =
        combine(baseGrouped.map { (base, targets) ->
            getLatestRates(base, targets.map { it.targetCurrency })
        }) { loadingStates ->
            val errors =
                loadingStates.filterIsInstance<LoadingState.Error>()
            val loadings =
                loadingStates.filterIsInstance<LoadingState.Loading>()
            val successes =
                loadingStates.filterIsInstance<LoadingState.Success<List<RatesItem>>>()
            when {
                loadings.isNotEmpty() -> LoadingState.Loading
                errors.size == loadingStates.size -> LoadingState.Error
                else -> {
                    val rates: List<RatesItem> = successes
                        .fold(emptyList()) { list, success ->
                            list + success.data
                        }
                    LoadingState.Success(rates)
                }
            }
        }

    internal fun onEvent(event: FavoritesScreenEvent) {
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