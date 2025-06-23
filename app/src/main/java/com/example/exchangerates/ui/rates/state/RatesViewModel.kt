@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.exchangerates.ui.rates.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.rates.api.RatesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val repository: RatesRepository,
) : ViewModel() {

    private val baseCurrency: MutableStateFlow<String?> = MutableStateFlow(null)

    private val ratesFlow = baseCurrency
        .filterNotNull()
        .flatMapLatest { baseCurrency ->
            repository.getLatestRates(baseCurrency)
        }

    private val currenciesFlow = repository.getCurrencyList()
        .onEach { state ->
            if (state is LoadingState.Success) {
                baseCurrency.value = state.data.firstOrNull()?.symbol
            }
        }

    val screenState: StateFlow<RatesScreenState> = combine(
        baseCurrency.filterNotNull(),
        ratesFlow,
        currenciesFlow,
    ) { baseCurrency, ratesState, currenciesState ->
        when (currenciesState) {
            LoadingState.Loading -> RatesScreenState.Loading
            LoadingState.Error -> RatesScreenState.Error
            is LoadingState.Success -> {
                when (ratesState) {
                    LoadingState.Loading -> RatesScreenState.Loading
                    LoadingState.Error -> RatesScreenState.Error
                    is LoadingState.Success -> RatesScreenState.Success(
                        baseCurrency = baseCurrency,
                        rates = ratesState.data,
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
            is RatesScreenEvent.OnFavoriteClick -> {
                // TODO: Implement favorite toggle
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