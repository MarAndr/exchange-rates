@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.exchangerates.ui.rates.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.favorites.api.FavoritePairsRepository
import com.example.exchangerates.features.favorites.api.model.FavoritePair
import com.example.exchangerates.features.rates.api.RatesRepository
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
    private val repository: RatesRepository,
    private val favoriteRepository: FavoritePairsRepository,
) : ViewModel() {

    private val baseCurrency: MutableStateFlow<String?> = MutableStateFlow(null)

    private val ratesFlow = baseCurrency
        .filterNotNull()
        .flatMapLatest { baseCurrency ->
            repository.getLatestRates(baseCurrency)
        }
        .onStart { emit(LoadingState.Loading) }

    private val currenciesFlow = repository.getCurrencyList()
        .onEach { state ->
            if (state is LoadingState.Success) {
                baseCurrency.value = state.data.firstOrNull()?.symbol
            }
        }

    val screenState: StateFlow<RatesScreenState> = combine(
        baseCurrency,
        ratesFlow,
        currenciesFlow,
        favoriteRepository.getPairs(),
    ) { baseCurrency, ratesState, currenciesState, favoritePairs ->
        when (currenciesState) {
            LoadingState.Loading -> RatesScreenState.Loading
            LoadingState.Error -> RatesScreenState.Error
            is LoadingState.Success -> {
                when (ratesState) {
                    LoadingState.Loading -> RatesScreenState.Loading
                    LoadingState.Error -> RatesScreenState.Error
                    is LoadingState.Success -> RatesScreenState.Success(
                        baseCurrency = baseCurrency.orEmpty(),
                        rates = ratesState.data.map { rate ->
                            val favoriteId = favoritePairs
                                .find { it.baseCurrency == baseCurrency && it.targetCurrency == rate.symbol }
                                ?.id
                            RatesUiModel(
                                symbol = rate.symbol,
                                rate = rate.rate,
                                favoriteId = favoriteId,
                            )
                        },
                        availableCurrencies = currenciesState.data,
                    )
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = RatesScreenState.Loading
    )

    fun onEvent(event: RatesScreenEvent) {
        when (event) {
            // todo provide dispatchers.io from DI
            is RatesScreenEvent.OnFavoriteClick -> viewModelScope.launch(Dispatchers.IO) {
                if (event.rate.favoriteId != null) {
                    val pair = FavoritePair(
                        id = event.rate.favoriteId,
                        baseCurrency = event.baseCurrency,
                        targetCurrency = event.rate.symbol,
                    )
                    favoriteRepository.removePair(pair)
                } else {
                    val pair = FavoritePair(
                        baseCurrency = event.baseCurrency,
                        targetCurrency = event.rate.symbol,
                    )
                    favoriteRepository.addPair(pair)
                }
            }

            is RatesScreenEvent.OnRefresh -> {
                // todo
            }

            is RatesScreenEvent.OnBaseCurrencyChanged -> {
                baseCurrency.value = event.newBaseCurrency
            }
        }
    }
}