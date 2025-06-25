package com.example.exchangerates.ui.home.favorites.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.rates.api.RatesRepository
import com.example.exchangerates.features.rates.api.model.RatesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val favoriteRepository: FavoritePairsRepository,
) : ViewModel() {
    val screenState = favoriteRepository.getPairs()
        .flatMapLatest {
            val baseGrouped = it.groupBy { it.baseCurrency }

            combine(baseGrouped.map { (base, targets) ->
                ratesRepository.getLatestRates(base, targets.map { it.targetCurrency })
            }) { loadingStates ->
                val errors = loadingStates.filterIsInstance<LoadingState.Error>()
                val loadings = loadingStates.filterIsInstance<LoadingState.Loading>()
                val successes =
                    loadingStates.filterIsInstance<LoadingState.Success<List<RatesItem>>>()
                when {
                    loadingStates.isEmpty() -> FavoritesScreenState.Data(emptyList())
                    errors.size == loadingStates.size -> FavoritesScreenState.Error
                    loadings.size == loadingStates.size -> FavoritesScreenState.Loading
                    else -> {
                        val rates: List<RatesItem> = successes.fold(emptyList()) { list, success ->
                            list + success.data
                        }
                        FavoritesScreenState.Data(rates)
                    }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FavoritesScreenState.Loading,
        )

    fun onEvent(event: FavoritesScreenEvent) {
        when (event) {
            // todo dispatchers
            is FavoritesScreenEvent.RemoveFromFavorites -> viewModelScope.launch(Dispatchers.IO) {
                favoriteRepository.removePair(
                    baseCurrency = event.ratesItem.base,
                    targetCurrency = event.ratesItem.symbol,
                )
            }
        }
    }
}